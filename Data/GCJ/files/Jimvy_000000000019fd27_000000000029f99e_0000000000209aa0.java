import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Solution {
    public static void print_impossible(int t) {
        System.out.println("Case #" + t + ": IMPOSSIBLE");
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int t = 1; t <= T; t++) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            if (K == N+1 || K == (N*N - 1)) {
                print_impossible(t);
                continue;
            }
            if (N == 3) {
                if (K == 5 || K == 7) {
                    print_impossible(t);
                    continue;
                }
            }
            final int NN = N;
            final int tt = t;
            Solver cp = new Solver(new StateManager());
            IntVar[][] matrix = new IntVar[N][N];
            IntVar[][] matrixT = new IntVar[N][N];
            IntVar[] diag = new IntVar[N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++)
                    matrix[i][j] = new IntVarImpl(cp, 1, N);
                diag[i] = matrix[i][i];
            }
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++)
                    matrixT[i][j] = matrix[j][i];
                cp.post(new AllDifferentFW(matrix[i]));
                cp.post(new AllDifferentFW(matrixT[i]));
            }
            cp.post(new Sum(diag, K));
            DFSearch dfs = new DFSearch(cp.getStateManager(), firstFail(flatten(matrix)));
            dfs.onSolution(() -> {
                System.out.println("Case #" + tt + ": POSSIBLE");
                for (int i = 0; i < NN; i++) {
                    for (int j = 0; j < NN; j++) {
                        System.out.print(matrix[i][j].max() + " ");
                    }
                    System.out.println();
                }
            });
            SearchStatistics stat = dfs.solve(searchStatistics -> searchStatistics.numberOfSolutions() == 1);
            if (stat.numberOfSolutions() == 0) {
                print_impossible(t);
            }
        }
    }



    /*
     * INFORMATION
     * What follows is (part of) the source code of MiniCP, a lightweight constraint programming solver.
     * This solver is used in the course of LINGI2365 "Constraint programming" at the Universite catholique de Louvain,
     * where we learn to add features progressively and see the complete workings of a CP solver.
     * I have copy-pasted and adapted the code of MiniCP for this task inside this file.
     * The copyright holders are mostly indicated on the MiniCP repository.
     * https://www.minicp.org
     * https://bitbucket.org/minicp/minicp/src/master/
     * (The project changes every year; this version of the solver is based on the version of commit a2530733.
     * There have been some updates from this point - don't use them as they are partly broken.)
     */



    @FunctionalInterface
    public interface Procedure {
        /**
         * Calls the procedure
         */
        void call();
    }
    public static class Solver {
        private Queue<Constraint> propagationQueue = new ArrayDeque<>();
        private List<Procedure> fixPointListeners = new LinkedList<>();

        private final StateManager sm;

        private final StateStack<IntVar> vars;

        public Solver(StateManager sm) {
            this.sm = sm;
            vars = new StateStack<>(sm);
        }
        public StateManager getStateManager() {
            return sm;
        }
        void schedule(Constraint c) {
            if (c.isActive() && !c.isScheduled()) {
                c.setScheduled(true);
                propagationQueue.add(c);
            }
        }

        private void notifyFixPoint() {
            fixPointListeners.forEach(Procedure::call);
        }
        public void fixPoint() {
            notifyFixPoint();
            try {
                while (!propagationQueue.isEmpty()) {
                    propagate(propagationQueue.remove());
                }
            } catch (InconsistencyException e) {
                // empty the queue and unset the scheduled status
                while (!propagationQueue.isEmpty())
                    propagationQueue.remove().setScheduled(false);
                throw e;
            }
        }
        private void propagate(Constraint c) {
            c.setScheduled(false);
            if (c.isActive())
                c.propagate();
        }
        public void post(Constraint c) {
            post(c, true);
        }
        public void post(Constraint c, boolean enforceFixPoint) {
            c.post();
            if (enforceFixPoint) fixPoint();
        }
        public void post(BoolVar b) {
            b.assign(true);
            fixPoint();
        }
    }
    private static class StopSearchException extends RuntimeException {
        private static final long serialVersionUID = 2079205745523222197L;
    }
    public static class DFSearch {

        private Supplier<Procedure[]> branching;
        private StateManager sm;

        private List<Procedure> solutionListeners = new LinkedList<>();
        private List<Procedure> failureListeners = new LinkedList<>();

        /**
         * Creates a Depth First Search object with a given branching
         * that defines the search tree dynamically.
         *
         * @param sm the state manager that will be saved and restored
         *           at each node of the search tree
         * @param branching a generator of closures in charge of defining the ordered
         *                  children nodes at each node of the depth-first-search tree.
         *                  When it returns an empty array, a solution is found.
         *                  A backtrack occurs when a {@link InconsistencyException}
         *                  is thrown.
         */
        public DFSearch(StateManager sm, Supplier<Procedure[]> branching) {
            this.sm = sm;
            this.branching = branching;
        }

        /**
         * Adds a listener that is called on each solution.
         *
         * @param listener the closure to be called whenever a solution is found
         */
        public void onSolution(Procedure listener) {
            solutionListeners.add(listener);
        }

        private void notifySolution() {
            solutionListeners.forEach(Procedure::call);
        }

        private void notifyFailure() {
            failureListeners.forEach(Procedure::call);
        }

        private SearchStatistics solve(SearchStatistics statistics, Predicate<SearchStatistics> limit) {
            sm.withNewState(() -> {
                try {
                    dfs(statistics, limit);
                    statistics.setCompleted();
                } catch (StopSearchException | StackOverflowError ignored) {
                }
            });
            return statistics;
        }


        /**
         * Effectively start a depth first search
         * looking for every solution.
         *
         * @return an object with the statistics on the search
         */
        public SearchStatistics solve() {
            SearchStatistics statistics = new SearchStatistics();
            return solve(statistics, stats -> false);
        }

        /**
         * Effectively start a depth first search
         * with a given predicate called at each node
         * to stop the search when it becomes true.
         *
         * @param limit a predicate called at each node
         *             that stops the search when it becomes true
         * @return an object with the statistics on the search
         */
        public SearchStatistics solve(Predicate<SearchStatistics> limit) {
            SearchStatistics statistics = new SearchStatistics();
            return solve(statistics, limit);
        }

        /**
         * Runs a depth-first search over the search space, using an explicit stack.
         *
         * Together with {@link #expandNode(Stack, SearchStatistics)} and
         * {@link #expNode(Procedure, SearchStatistics, Stack)}, these three methods
         * implement a DFS with an explicit stack.
         * dfs is used to launch the search, expandNode to append the children of
         * the current node to the search, and expNode to explore a node.
         * @param statistics object used to collect the statistics of the search
         * @param limit      predicate telling, based on the search statistics,
         *                   if we should stop the exploration
         */
        private void dfs(SearchStatistics statistics, Predicate<SearchStatistics> limit) {
            Stack<Procedure> alternatives = new Stack<>();
            expandNode(alternatives,statistics); // root expension
            while (!alternatives.isEmpty()) {
                if (limit.test(statistics))
                    throw new StopSearchException();
                try {
                    alternatives.pop().call();
                } catch (InconsistencyException e) {
                    notifyFailure();
                    statistics.incrFailures();
                }
            }
        }

        /**
         * Expands the current node in the search by retrieving the branches
         * and pushing the operations on the explicit stack
         * @param alternatives a stack of operations to execute to explore
         *                     the search tree: saving states, exploring a node,
         *                     restoring to a previous state
         * @param statistics   object used to collect statistics of the search
         */
        private void expandNode(Stack<Procedure> alternatives, SearchStatistics statistics) {
            Procedure[] branches = branching.get();
            if (branches.length == 0) {
                statistics.incrSolutions();
                notifySolution();
            } else {
                /*il faut traverser les branches chronologiquement sinon ca fait n'importe quoi*/
                for (int i = branches.length - 1; i >= 0; i--) {
                    Procedure p = branches[i];
                    // FIXME understand why we cannot remove this pair
                    //  of save/restore, although we should be able to
                    //if (i < branches.length - 1)
                    alternatives.push(() -> sm.restoreState());
                    alternatives.push(expNode(p, statistics, alternatives));
                    //if (i < branches.length - 1)
                    alternatives.push(() -> sm.saveState());
                }
            }
        }

        /**
         * Explores the current node
         * @param p            procedure used to setup the node (e.g., constraints)
         * @param statistics   object used to collect statistics of the search
         * @param alternatives stack of operations to execute to explore the search
         *                     tree: saving states, exploring a node, restoring
         * @return a procedure that will call {@code p}, update the statistics, and
         * expand the node by calling {@link #expandNode(Stack, SearchStatistics)}
         */
        private Procedure expNode(Procedure p, SearchStatistics statistics, Stack<Procedure> alternatives) {
            return () -> {
                try {
                    statistics.incrNodes();
                    p.call();
                    expandNode(alternatives, statistics);
                }
                catch (InconsistencyException e) {
                    notifyFailure();
                    statistics.incrFailures();
                }
            };
        }

    }
    public static class SearchStatistics {

        private int nFailures = 0;
        private int nNodes = 0;
        private int nSolutions = 0;
        private boolean completed = false;

        public String toString() {
            return "\n\t#choice: " + nNodes
                    + "\n\t#fail: " + nFailures
                    + "\n\t#sols : " + nSolutions
                    + "\n\tcompleted : " + completed + "\n";
        }
        void incrFailures() {
            nFailures++;
        }
        void incrNodes() {
            nNodes++;
        }
        void incrSolutions() {
            nSolutions++;
        }
        void setCompleted() {
            completed = true;
        }
        public int numberOfSolutions() {
            return nSolutions;
        }

    }
    public interface StateEntry {
        void restore();
    }
    public static class TrailInt implements StateInt {
        class StateEntryInt implements StateEntry {
            private final int v;

            StateEntryInt(int v) {
                this.v = v;
            }

            @Override
            public void restore() {
                TrailInt.this.v = v;
            }
        }

        private StateManager trail;
        private int v;
        private long lastMagic = -1L;

        TrailInt(StateManager trail, int initial) {
            this.trail = trail;
            v = initial;
            lastMagic = trail.getMagic() - 1;
        }

        private void trail() {
            long trailMagic = trail.getMagic();
            if (lastMagic != trailMagic) {
                lastMagic = trailMagic;
                trail.pushState(new StateEntryInt(v));
            }
        }

        @Override
        public int setValue(int v) {
            if (v != this.v) {
                trail();
                this.v = v;
            }
            return this.v;
        }

        @Override
        public int increment() {
            return setValue(value() + 1);
        }

        @Override
        public int decrement() {
            return setValue(value() - 1);
        }

        @Override
        public int value() {
            return this.v;
        }

        @Override
        public String toString() {
            return "" + v;
        }
    }
    public static class TrailBool implements StateBool {

        private final StateEntry restoreTrue = new StateEntry() {
            @Override
            public void restore() {
                v = true;
            }
        };

        private final StateEntry restoreFalse = new StateEntry() {
            @Override
            public void restore() {
                v = false;
            }
        };

        private boolean v;
        private StateManager trail;
        private long lastMagic;

        TrailBool(StateManager context, boolean initial) {
            this.trail = context;
            v = initial;
            lastMagic = context.getMagic() - 1;
        }

        private void trail() {
            long contextMagic = trail.getMagic();
            if (lastMagic != contextMagic) {
                lastMagic = contextMagic;
                if (v) trail.pushState(restoreTrue);
                else trail.pushState(restoreFalse);
            }
        }

        @Override
        public void setValue(boolean v) {
            if (v != this.v) {
                trail();
                this.v = v;
            }
        }

        @Override
        public boolean value() {
            return this.v;
        }


    }
    public static class StateManager {

        static class Backup extends Stack<StateEntry> {
            Backup() {
            }

            void restore() {
                for (StateEntry se : this)
                    se.restore();
            }
        }
        private Stack<Backup> prior;
        private Backup current;
        private long magic = 0L;
        private List<Procedure> onRestoreListeners;
        public StateManager() {
            prior = new Stack<>();
            current = new Backup();
            onRestoreListeners = new LinkedList<>();
        }
        private void notifyRestore() {
            for (Procedure l : onRestoreListeners) {
                l.call();
            }
        }
        long getMagic() {
            return magic;
        }
        void pushState(StateEntry entry) {
            current.push(entry);
        }
        int getLevel() {
            return prior.size() - 1;
        }
        public void saveState() {
            prior.add(current);
            current = new Backup();
            magic++;
        }
        public void restoreState() {
            current.restore();
            current = prior.pop();
            magic++;
            notifyRestore();
        }
        void withNewState(Procedure body) {
            final int level = getLevel();
            saveState();
            body.call();
            restoreStateUntil(level);
        }
        void restoreStateUntil(int level) {
            while (getLevel() > level)
                restoreState();
        }
        public StateInt makeStateInt(int initValue) {
            return new TrailInt(this, initValue);
        }
        StateBool makeStateBool(boolean initValue) {
            return new TrailBool(this, initValue);
        }
    }

    public interface Constraint {
        void post();
        void propagate();
        void setScheduled(boolean scheduled);
        boolean isScheduled();
        void setActive(boolean active);
        boolean isActive();
    }
    public static abstract class AbstractConstraint implements Constraint {
        private final Solver cp;
        private boolean scheduled = false;
        private final StateBool active;
        public AbstractConstraint(Solver cp) {
            this.cp = cp;
            active = cp.getStateManager().makeStateBool(true);
        }
        public void post() {
        }
        public Solver getSolver() {
            return cp;
        }
        public void propagate() {
        }
        public void setScheduled(boolean scheduled) {
            this.scheduled = scheduled;
        }
        public boolean isScheduled() {
            return scheduled;
        }
        public void setActive(boolean active) {
            this.active.setValue(active);
        }
        public boolean isActive() {
            return active.value();
        }
    }
    public static class AllDifferentFW extends AbstractConstraint {
        private IntVar[] x;
        private StateInt nUnBounds ;
        private int[] unBounds;
        private int n;
        public AllDifferentFW(IntVar... x) {
            super(x[0].getSolver());
            this.x = x;
            this.n = x.length;
            nUnBounds = getSolver().getStateManager().makeStateInt(n);
            unBounds = IntStream.range(0, n).toArray();
        }
        @Override
        public void post() {
            for (IntVar var : x)
                var.propagateOnBind(this);
            propagate();
        }
        @Override
        public void propagate() {
            // Filter the unbound vars and update the partial sum
            int nU = nUnBounds.value();
            for (int i = nU - 1; i >= 0; i--) {
                int idx = unBounds[i];
                IntVar y =x[idx];
                if (y.isBound()) {
                    int yValue = y.min();
                    for (int j =0 ; j< nU;j++){
                        if (unBounds[j] != idx ) x[unBounds[j]].remove(yValue);
                    }
                    unBounds[i] = unBounds[nU - 1]; // Swap the variables
                    unBounds[nU - 1] = idx;
                    nU--;
                }
            }
            nUnBounds.setValue(nU);

        }

    }
    public static IntVar minus(IntVar x) {
        return new IntVarViewOpposite(x);
    }
    public static class IntVarViewOpposite implements IntVar {
        private final IntVar x;
        public IntVarViewOpposite(IntVar x) {
            this.x = x;
        }
        @Override
        public Solver getSolver() {
            return x.getSolver();
        }

        @Override
        public void whenBind(Procedure f) {
            x.whenBind(f);
        }

        @Override
        public void whenBoundsChange(Procedure f) {
            x.whenBoundsChange(f);
        }

        @Override
        public void whenDomainChange(Procedure f) {
            x.whenDomainChange(f);
        }

        @Override
        public void propagateOnDomainChange(Constraint c) {
            x.propagateOnDomainChange(c);
        }

        @Override
        public void propagateOnBind(Constraint c) {
            x.propagateOnBind(c);
        }

        @Override
        public void propagateOnBoundChange(Constraint c) {
            x.propagateOnBoundChange(c);
        }

        @Override
        public int min() {
            return -x.max();
        }

        @Override
        public int max() {
            return -x.min();
        }

        @Override
        public int size() {
            return x.size();
        }

        @Override
        public int fillArray(int[] dest) {
            int s = x.fillArray(dest);
            for (int i = 0; i < s; i++) {
                dest[i] = -dest[i];
            }
            return s;
        }

        @Override
        public boolean isBound() {
            return x.isBound();
        }

        @Override
        public boolean contains(int v) {
            return x.contains(-v);
        }

        @Override
        public void remove(int v) {
            x.remove(-v);
        }

        @Override
        public void assign(int v) {
            x.assign(-v);
        }

        @Override
        public void removeBelow(int v) {
            x.removeAbove(-v);
        }

        @Override
        public void removeAbove(int v) {
            x.removeBelow(-v);
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append("{");
            for (int i = min(); i <= max() - 1; i++) {
                if (contains((i))) {
                    b.append(i);
                    b.append(',');
                }
            }
            if (size() > 0) b.append(max());
            b.append("}");
            return b.toString();
        }
    }
    public static class Sum extends AbstractConstraint {
        private int[] unBounds;
        private StateInt nUnBounds;
        private StateInt sumBounds;
        private IntVar[] x;
        private int n;
        /**
         * Creates a sum constraint.
         * <p> This constraint holds iff
         * {@code x[0]+x[1]+...+x[x.length-1] == y}.
         *
         * @param x the non empty left hand side of the sum
         * @param y the right hand side of the sum
         */
        public Sum(IntVar[] x, IntVar y) {
            this(Arrays.copyOf(x, x.length + 1));
            this.x[x.length] = minus(y);
        }
        /**
         * Creates a sum constraint.
         * <p> This constraint holds iff
         * {@code x[0]+x[1]+...+x[x.length-1] == y}.
         *
         * @param x the non empty left hand side of the sum
         * @param y the right hand side of the sum
         */
        public Sum(IntVar[] x, int y) {
            this(Arrays.copyOf(x, x.length + 1));
            this.x[x.length] = new IntVarImpl(getSolver(), -y, -y);
        }
        /**
         * Creates a sum constraint.
         * <p> This constraint holds iff
         * {@code x[0]+x[1]+...+x[x.length-1] == 0}.
         *
         * @param x the non empty set of variables that should sum to zero
         */
        public Sum(IntVar[] x) {
            super(x[0].getSolver());
            this.x = x;
            this.n = x.length;
            nUnBounds = getSolver().getStateManager().makeStateInt(n);
            sumBounds = getSolver().getStateManager().makeStateInt(0);
            unBounds = IntStream.range(0, n).toArray();
        }

        @Override
        public void post() {
            for (IntVar var : x)
                var.propagateOnBoundChange(this);
            propagate();
        }

        @Override
        public void propagate() {
            // Filter the unbound vars and update the partial sum
            int nU = nUnBounds.value();
            int sumMin = sumBounds.value(), sumMax = sumBounds.value();
            for (int i = nU - 1; i >= 0; i--) {
                int idx = unBounds[i];
                sumMin += x[idx].min(); // Update partial sum
                sumMax += x[idx].max();
                if (x[idx].isBound()) {
                    sumBounds.setValue(sumBounds.value() + x[idx].min());
                    unBounds[i] = unBounds[nU - 1]; // Swap the variables
                    unBounds[nU - 1] = idx;
                    nU--;
                }
            }
            nUnBounds.setValue(nU);
            if (sumMin > 0 || sumMax < 0)
                throw new InconsistencyException();
            for (int i = nU - 1; i >= 0; i--) {
                int idx = unBounds[i];
                x[idx].removeAbove(-(sumMin - x[idx].min()));
                x[idx].removeBelow(-(sumMax - x[idx].max()));
            }
        }
    }
    public static class InconsistencyException extends RuntimeException {
        static final InconsistencyException INCONSISTENCY = new InconsistencyException();
        private static final long serialVersionUID = 1240061199250453776L;
        public String toString() {
            return "inconsistency";
        }
        /**
         * Forbid the JVM to produce a stack trace each time an InconsistencyException is thrown.
         *
         * Uncomment if you need stack traces. Be careful to comment it after debugging.
         */
        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }
    public interface BoolVar extends IntVar {
        boolean isTrue();
        boolean isFalse();
        void assign(boolean b);
    }
    public interface StateBool {
        void setValue(boolean v);
        boolean value();
    }
    public interface StateInt {
        int setValue(int v);
        int value();
        int increment();
        int decrement();
    }
    public static class StateStack<E> {
        private StateInt size;
        private ArrayList<E> stack;
        /**
         * Creates a restorable stack.
         * @param sm the state manager that saves/restores the stack
         *         when {@link StateManager#saveState()} / {@link StateManager#restoreState()}
         *         methods are called.
         */
        StateStack(StateManager sm) {
            size = sm.makeStateInt(0);
            stack = new ArrayList<E>();
        }
        public void push(E elem) {
            int s = size.value();
            if (stack.size() > s) stack.set(s, elem);
            else stack.add(elem);
            size.increment();
        }
        public int size() {
            return size.value();
        }
        public E get(int index) {
            return stack.get(index);
        }
    }
    public interface DomainListener {
        void empty();
        void bind();
        void change();
        void changeMin();
        void changeMax();
    }
    public static class ConstraintClosure extends AbstractConstraint {
        private final Procedure filtering;
        ConstraintClosure(Solver cp, Procedure filtering) {
            super(cp);
            this.filtering = filtering;
        }
        @Override
        public void post() {
        }
        @Override
        public void propagate() {
            filtering.call();
        }
    }
    public interface IntVar {
        Solver getSolver();
        void whenBind(Procedure f);
        void whenBoundsChange(Procedure f);
        void whenDomainChange(Procedure f);
        void propagateOnDomainChange(Constraint c);
        void propagateOnBind(Constraint c);
        void propagateOnBoundChange(Constraint c);
        int min();
        int max();
        int size();
        int fillArray(int[] dest);
        boolean isBound();
        boolean contains(int v);
        void remove(int v);
        void assign(int v);
        void removeBelow(int v);
        void removeAbove(int v);
    }
    public static class IntVarImpl implements IntVar {
        private Solver cp;
        private SparseSetDomain domain;
        private StateStack<Constraint> onDomain;
        private StateStack<Constraint> onBind;
        private StateStack<Constraint> onBounds;
        private DomainListener domListener = new DomainListener() {
            @Override
            public void empty() {
                throw InconsistencyException.INCONSISTENCY; // Integer Vars cannot be empty
            }
            @Override
            public void bind() {
                scheduleAll(onBind);
            }
            @Override
            public void change() {
                scheduleAll(onDomain);
            }
            public void changeMin() {
                scheduleAll(onBounds);
            }
            @Override
            public void changeMax() {
                scheduleAll(onBounds);
            }
        };
        /**
         * Creates a variable with the elements {@code {0,...,n-1}}
         * as initial domain.
         *
         * @param cp the solver in which the variable is created
         * @param n  the number of values with {@code n > 0}
         */
        public IntVarImpl(Solver cp, int n) {
            this(cp, 0, n - 1);
        }

        /**
         * Creates a variable with the elements {@code {min,...,max}}
         * as initial domain.
         *
         * @param cp the solver in which the variable is created
         * @param min the minimum value of the domain
         * @param max the maximum value of the domain with {@code max >= min}
         */
        public IntVarImpl(Solver cp, int min, int max) {
            if (min > max) throw new InvalidParameterException("at least one setValue in the domain");
            this.cp = cp;
            domain = new SparseSetDomain(cp.getStateManager(), min, max);
            onDomain = new StateStack<>(cp.getStateManager());
            onBind = new StateStack<>(cp.getStateManager());
            onBounds = new StateStack<>(cp.getStateManager());
        }



        /**
         * Creates a variable with a given set of values as initial domain.
         *
         * @param cp the solver in which the variable is created
         * @param values the initial values in the domain, it must be nonempty
         */
        public IntVarImpl(Solver cp, Set<Integer> values) {
            Integer[] vals = values.toArray(new Integer[0]);
            Arrays.sort(vals);
            this.cp = cp;
            domain = new SparseSetDomain(cp.getStateManager(), vals[0], vals[vals.length - 1]);
            onDomain = new StateStack<>(cp.getStateManager());
            onBind = new StateStack<>(cp.getStateManager());
            onBounds = new StateStack<>(cp.getStateManager());
            for (int i = vals[0]; i < vals[vals.length - 1]; i++)
                if (!values.contains(i))
                    domain.remove(i, domListener);
        }
        public Solver getSolver() {
            return cp;
        }
        public boolean isBound() {
            return domain.size() == 1;
        }
        public String toString() {
            return domain.toString();
        }
        public void whenBind(Procedure f) {
            onBind.push(constraintClosure(f));
        }
        public void whenBoundsChange(Procedure f) {
            onBounds.push(constraintClosure(f));
        }
        public void whenDomainChange(Procedure f) {
            onDomain.push(constraintClosure(f));
        }
        private Constraint constraintClosure(Procedure f) {
            Constraint c = new ConstraintClosure(cp, f);
            getSolver().post(c, false);
            return c;
        }
        public void propagateOnDomainChange(Constraint c) {
            onDomain.push(c);
        }
        public void propagateOnBind(Constraint c) {
            onBind.push(c);
        }
        public void propagateOnBoundChange(Constraint c) {
            onBounds.push(c);
        }
        void scheduleAll(StateStack<Constraint> constraints) {
            for (int i = 0; i < constraints.size(); i++)
                cp.schedule(constraints.get(i));
        }
        public int min() {
            return domain.min();
        }
        public int max() {
            return domain.max();
        }
        public int size() {
            return domain.size();
        }
        public int fillArray(int[] dest) {
            return domain.fillArray(dest);
        }
        public boolean contains(int v) {
            return domain.contains(v);
        }
        public void remove(int v) {
            domain.remove(v, domListener);
        }
        public void assign(int v) {
            domain.removeAllBut(v, domListener);
        }
        public void removeBelow(int v) {
            domain.removeBelow(v, domListener);
        }
        public void removeAbove(int v) {
            domain.removeAbove(v, domListener);
        }
    }
    public static class StateSparseSet {
        private int[] values;
        private int[] indexes;
        private StateInt size;
        private StateInt min;
        private StateInt max;
        private int ofs;
        private int n;
        /**
         * Creates a set containing the elements {@code {ofs,ofs+1,...,ofs+n-1}}.
         *
         * @param sm the state manager that will save and restore the set when
         *        {@link StateManager#saveState()} / {@link StateManager#restoreState()}
         *           mehtods are called
         * @param n  the number of elements in the set
         * @param ofs the minimum value in the set containing {@code {ofs,ofs+1,...,ofs+n-1}}
         */
        public StateSparseSet(StateManager sm, int n, int ofs) {
            this.n = n;
            this.ofs = ofs;
            size = sm.makeStateInt(n);
            min = sm.makeStateInt(0);
            max = sm.makeStateInt(n - 1);
            values = new int[n];
            indexes = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = i;
                indexes[i] = i;
            }
        }
        private void exchangePositions(int val1, int val2) {
            assert (checkVal(val1));
            assert (checkVal(val2));
            int v1 = val1;
            int v2 = val2;
            int i1 = indexes[v1];
            int i2 = indexes[v2];
            values[i1] = v2;
            values[i2] = v1;
            indexes[v1] = i2;
            indexes[v2] = i1;
        }
        private boolean checkVal(int val) {
            assert (val <= values.length - 1);
            return true;
        }
        /**
         * Returns an array with the values present in the set.
         *
         * @return an array representation of the values present in the set
         */
        public int[] toArray() {
            int[] res = new int[size()];
            fillArray(res);
            return res;
        }

        /**
         * Sets the first values of <code>dest</code> to the ones
         * present in the set.
         *
         * @param dest, an array large enough {@code dest.length >= size()}
         * @return the size of the set
         */
        public int fillArray(int[] dest) {
            System.arraycopy(values, 0, dest, 0, size.value());
            int s = size.value();
            if (ofs != 0) {
                for (int i = 0; i < s; i++)
                    dest[i] = dest[i] + ofs;
            }
            return s;
        }
        public boolean isEmpty() {
            return size.value() == 0;
        }
        public int size() {
            return size.value();
        }
        public int min() {
            if (isEmpty())
                throw new NoSuchElementException();
            return min.value() + ofs;
        }
        public int max() {
            if (isEmpty())
                throw new NoSuchElementException();
            else return max.value() + ofs;
        }

        private void updateBoundsValRemoved(int val) {
            updateMaxValRemoved(val);
            updateMinValRemoved(val);
        }

        private void updateMaxValRemoved(int val) {
            if (!isEmpty() && max.value() == val) {
                assert (!internalContains(val));
                //the maximum was removed, search the new one
                for (int v = val - 1; v >= min.value(); v--) {
                    if (internalContains(v)) {
                        max.setValue(v);
                        return;
                    }
                }
            }
        }

        private void updateMinValRemoved(int val) {
            if (!isEmpty() && min.value() == val) {
                assert (!internalContains(val));
                //the minimum was removed, search the new one
                for (int v = val + 1; v <= max.value(); v++) {
                    if (internalContains(v)) {
                        min.setValue(v);
                        return;
                    }
                }
            }
        }

        /**
         * Removes the given value from the set.
         *
         * @param val the value to remove.
         * @return true if val was in the set, false otherwise
         */
        public boolean remove(int val) {
            if (!contains(val))
                return false; //the setValue has already been removed
            val -= ofs;
            assert (checkVal(val));
            int s = size();
            exchangePositions(val, values[s - 1]);
            size.decrement();
            updateBoundsValRemoved(val);
            return true;
        }

        /**
         * This method operates on the shifted value (one cannot shift now).
         *
         * @param val the setValue to lookup for membership
         * @return true if val is in the set, false otherwise
         */
        private boolean internalContains(int val) {
            if (val < 0 || val >= n)
                return false;
            else
                return indexes[val] < size();
        }

        /**
         * Checks if a value is in the set.
         *
         * @param val the value to check
         * @return true if val is in the set
         */
        public boolean contains(int val) {
            val -= ofs;
            if (val < 0 || val >= n)
                return false;
            else
                return indexes[val] < size();
        }

        /**
         * Removes all the element from the set except the given value.
         *
         * @param v is an element in the set
         */
        void removeAllBut(int v) {
            // we only have to put in first position this setValue and set the size to 1
            assert (contains(v));
            v -= ofs;
            assert (checkVal(v));
            int val = values[0];
            int index = indexes[v];
            indexes[v] = 0;
            values[0] = v;
            indexes[val] = index;
            values[index] = val;
            min.setValue(v);
            max.setValue(v);
            size.setValue(1);
        }

        /**
         * Removes all the values in the set.
         */
        void removeAll() {
            size.setValue(0);
        }

        /**
         * Remove all the values less than the given value from the set
         *
         * @param value a value such that all the ones smaller are removed
         */
        public void removeBelow(int value) {
            if (max() < value) {
                removeAll();
            } else {
                for (int v = min(); v < value; v++) {
                    remove(v);
                }
            }
        }

        /**
         * Remove all the values larger than the given value from the set
         *
         * @param value a value such that all the ones greater are removed
         */
        public void removeAbove(int value) {
            if (min() > value) {
                removeAll();
            } else {
                int max = max();
                for (int v = value + 1; v <= max; v++) {
                    remove(v);
                }
            }
        }


        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append("{");
            for (int i = 0; i < size() - 1; i++) {
                b.append(values[i] + ofs);
                b.append(',');
            }
            if (size() > 0) b.append(values[size() - 1] + ofs);
            b.append("}");
            return b.toString();
        }
    }
    public static class SparseSetDomain {
        private StateSparseSet domain;


        public SparseSetDomain(StateManager sm, int min, int max) {
            domain = new StateSparseSet(sm, max - min + 1, min);
        }
        public int fillArray(int[] dest) {
            return domain.fillArray(dest);
        }
        public int min() {
            return domain.min();
        }
        public int max() {
            return domain.max();
        }
        public int size() {
            return domain.size();
        }
        public boolean contains(int v) {
            return domain.contains(v);
        }
        public boolean isBound() {
            return domain.size() == 1;
        }
        public void remove(int v, DomainListener l) {
            if (domain.contains(v)) {
                boolean maxChanged = max() == v;
                boolean minChanged = min() == v;
                domain.remove(v);
                if (domain.size() == 0)
                    l.empty();
                l.change();
                if (maxChanged) l.changeMax();
                if (minChanged) l.changeMin();
                if (domain.size() == 1) l.bind();
            }
        }
        void removeAllBut(int v, DomainListener l) {
            if (domain.contains(v)) {
                if (domain.size() != 1) {
                    boolean maxChanged = max() != v;
                    boolean minChanged = min() != v;
                    domain.removeAllBut(v);
                    if (domain.size() == 0)
                        l.empty();
                    l.bind();
                    l.change();
                    if (maxChanged) l.changeMax();
                    if (minChanged) l.changeMin();
                }
            } else {
                domain.removeAll();
                l.empty();
            }
        }
        public void removeBelow(int value, DomainListener l) {
            if (domain.min() < value) {
                domain.removeBelow(value);
                switch (domain.size()) {
                    case 0:
                        l.empty();
                        break;
                    case 1:
                        l.bind();
                    default:
                        l.changeMin();
                        l.change();
                        break;
                }
            }
        }
        public void removeAbove(int value, DomainListener l) {
            if (domain.max() > value) {
                domain.removeAbove(value);
                switch (domain.size()) {
                    case 0:
                        l.empty();
                        break;
                    case 1:
                        l.bind();
                    default:
                        l.changeMax();
                        l.change();
                        break;
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append("{");
            for (int i = min(); i <= max() - 1; i++)
                if (contains((i)))
                    b.append(i).append(',');
            if (size() > 0) b.append(max());
            b.append("}");
            return b.toString();
        }

    }
    public static void equal(IntVar x, int v) {
        x.assign(v);
        x.getSolver().fixPoint();
    }
    public static void notEqual(IntVar x, int v) {
        x.remove(v);
        x.getSolver().fixPoint();
    }
    public static final Procedure[] EMPTY = new Procedure[0];
    public static Procedure[] branch(Procedure... branches) {
        return branches;
    }
    public static <T, N extends Comparable<N>> T selectMin(T[] x, Predicate<T> p, Function<T, N> f) {
        T sel = null;
        for (T xi : x) {
            if (p.test(xi)) {
                sel = sel == null || f.apply(xi).compareTo(f.apply(sel)) < 0 ? xi : sel;
            }
        }
        return sel;
    }
    public static Supplier<Procedure[]> firstFail(IntVar... x) {
        return () -> {
            IntVar xs = selectMin(x,
                    xi -> xi.size() > 1,
                    IntVar::size);
            if (xs == null)
                return EMPTY;
            else {
                int v = xs.min();
                return branch(
                        () -> equal(xs, v),
                        () -> notEqual(xs, v)
                );
            }
        };
    }
    private static IntVar[] flatten(IntVar[][] x) {
        return Arrays.stream(x).flatMap(Arrays::stream).toArray(IntVar[]::new);
    }
}