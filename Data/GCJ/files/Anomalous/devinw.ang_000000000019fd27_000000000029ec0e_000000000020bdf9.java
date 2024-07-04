public void solve(int testNumber, FastReader sc, PrintWriter pw) {
    int n = sc.nextInt();
    PriorityQueue<Event> events = new PriorityQueue<>();
    int[] schedule = new int[n];
    int active1 = -2, active2 = -2;

    for (int i = 0; i < n; i++) {
        events.add(new Event(sc.nextInt(), i + 1));
        events.add(new Event(sc.nextInt(), -i - 1));
    }

    while (!events.isEmpty()) {
        Event current = events.poll();
        if (current.type > 0) {
            if (active1 == -2) {
                active1 = current.type;
            } else if (active2 == -2) {
                active2 = current.type;
                schedule[current.type - 1] = 1;
            } else {
                pw.printf("Case #%d: IMPOSSIBLE%n", testNumber);
                return;
            }
        } else {
            if (active1 == -current.type) {
                active1 = -2;
            }
            if (active2 == -current.type) {
                active2 = -2;
            }
        }
    }

    pw.printf("Case #%d: ", testNumber);
    for (int i : schedule) {
        pw.print(i == 0 ? 'C' : 'J');
    }
    pw.println();
}

static class Event implements Comparable<Event> {
    int time, type;

    Event(int time, int type) {
        this.time = time;
        this.type = type;
    }

    @Override
    public int compareTo(Event other) {
        return this.time == other.time ? Integer.compare(this.type, other.type) : Integer.compare(this.time, other.time);
    }
}

static class FastReader {
    BufferedReader br;
    StringTokenizer st;

    public FastReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public FastReader(String fileName) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(new File(fileName)));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

    String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}