import java.awt.image.BufferStrategy;
import java.awt.Graphics;

public class Game implements Runnable {// the actual class for the game, has the most stuff
    private Display display;
    // a thread is basically a program, the game class will run on a separate
    // thread, so it will be running on a separate program and work
    // independently(this is done for efficiency)
    // runnable was implemented to allow the game class to have a thread

    private int width, height; // made public, because you want the dimensions of the window
    // to be known between classes

    public String title;
    // string variable to be called on to make the display

    private boolean running = false;
    // boolean variable for game loop to be able to be turned off easily

    private Thread thread; // making a thread variable

    private BufferStrategy bs; // a buffer strategy tells the computer how to draw things to the screen by
                               // using buffers
    // a buffer is a hidden computer screen, an invisible screen with data behind
    // the actual screen
    // buffers draws stuff to other buffers until it goes to the screen... this is
    // done to avoid screen flickering(so the updating isn't visible)
    // variables needed for the init method
    private Graphics g;
    // a graphics object is basically a paintbrush, it can draw a bunch of shapes

    int x = 0;// used to keep track of stuff for tick method
    
    //Input
    private KeyManager keyManager;// key manager to be used as a parameter to call add a key listener to a display
    private MouseManager mouseManager;

    private GameCamera gameCamera;// this is the game camera

    private Handler handler;// this will be used in order to access variables

    // States
    public State gameState; // declaring a state, every state can be called a state
    public State menuState; // the menu state
    public State settingsState;



    public Game(String title, int width, int height) { // these parameters exist, because the constructor method
        // from the Display class has these 3 parameters(we didn't make a default
        // constructor, so these parameters are necessary)
        this.width = width;
        this.height = height;// using 'this' in order to specify, because one doesn't have a name
        this.title = title; // sets the input parameter to the public variable to be used later
        keyManager = new KeyManager();
        mouseManager = new MouseManager();

    }

    private void init() {// method needed to run the 'run' method, only runs once, it's private because
                         // it's only needed for one class' method
        // initializes the graphics for the game
        display = new Display(title, width, height);// makes the display for the game(every time the game method makes
                                                    // init run, there will be a new window)
        // testImage = ImageLoader.loadImage("C:\\Java Stuff\\First App\\res(stands for
        // resources and holds stuff for the program)\\textures(holds the
        // pictures)\\BobRoss.jpg");//loads in the picture from the file path by using
        // the image loader class, make sure that your file path stuff is correct before
        // doing this
        display.getFrame().addKeyListener(keyManager);// adds a key listener to a display
        display.getFrame().addMouseListener(mouseManager);//adds the mouse detecting stuff to both the 
        //frames and canvases(done to both, so things don't get desynchronized)
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        Assets.init();// loads in the beginning scene

        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0, 0);// initializes the gameCamera variable at the position of 0, 0

        gameState = new GameState(handler);// instantiates the state to that class' state
        menuState = new MenuState(handler);
        settingsState = new SettingsState(handler);// uses this game class as the parameter, since every class will
                                                   // basically have this game object

        State.setState(menuState);// sets the current state to the gamestate variable we made, the first state
    }

    private void tick() {// a method that will update the game, can also be called 'update', needed for
                         // game loop, used to make sure the game runs the same speed for every computer,
                         // regardless of how fast or slow
        keyManager.tick(); // tick the key manager as well and have it run

        if (State.getState() != null)// if there is a current state, tick for that state
            State.getState().tick();// the code for this state's tick is in their respective class
    }

    private void render() {// a method that will keep rendering things, bringing things to the screen,
                           // needed for game loop, also it's kept in its own method to keep things
                           // organized, since this code is so large lmao
        bs = display.getCanvas().getBufferStrategy(); // buffer strategy variable set to the buffer strategy of the
                                                      // canvas
        // since this is looped, the bs variable will keep getting changed into the new
        // one existing in the canvas
        // get buffer strategy gets the amount of buffers being used

        if (bs == null) {
            display.getCanvas().createBufferStrategy(3); // if there are no buffer screens, then make 3 new ones
            return; // this is done to avoid errors
        }
        g = bs.getDrawGraphics();

        g.clearRect(0, 0, width, height);// clears the entire screen every time, by making a deletion rectangle that
                                         // takes up the whole window
        // gets drawing tools of buffer, like paint for the paintbrush

        // Draw Here
        // 0,0 position is top left of the screen and coordinates are measured in
        // pixels, same thing except y goes down
        // g.drawRect(x, y, width, height); draws a rectangle
        // g.setColor(c); picks up a new color, new paintbrush
        // g.fillRect(x, y, width, height); draws a rectangle with the color you are
        // holding

        // g.drawImage(test, 0, 0, null); //drawing images has 4 parameters, the image,
        // the x and y coordinates, and an observer(just put null for now)
        // g.drawImage(sheet.crop(32, 25, 24, 50), 0, 0, null);//this command draws the
        // cropped version of the image, is an example for how the spritesheet class
        // works
        // every state will have these commands applied

        // End Drawing
        if (State.getState() != null)// if there is a current state, we render for the state
            State.getState().render(g);// the code for that state's render is in their respective class

        bs.show(); // shows the picture drawn
        g.dispose(); // makes sure the graphics object is ended properly

    }

    public void run() {// every time runnable is implemented, a run method is required or it won't run
        // this is where most of the code for the game will be
        init();

        int fps = 60;// how many times the tick and rendor method will work per second, since each
                     // tick and render running is equal to one frame
        double timePerTick = 1000000000 / fps; // 1 billion nano seconds in one second; we convert the fps to frames per
                                               // nanosecond in order to make things specific
        // this gives the max amount of nano seconds per frame to get 60 fps
        double delta = 0; // this will keep track of the amount of time left before the tick and render
                          // method can be allowed to run again
        long now;
        long lastTime = System.nanoTime();// variable to keep track of the last time stamp set to the computer's clock
                                          // in nano seconds
        long timer = 0;
        int ticks = 0;

        // a game loop is a loop that constantly updates the screen and variables, every
        // game needs one obviously
        while (running) { // while running variable is true, the game continues, else it stops
            now = System.nanoTime(); // this is done to update the amount of time the game has been running
            delta += (now - lastTime) / timePerTick; // adds the time elapsed over the max allowed time to the time
                                                     // allowed till the next tick
            timer += (now - lastTime); // keeps track
            lastTime = now; // to update the lastTime variable

            if (delta >= 1) { // if the amount of time allowed until the next tick is this 1 or more times
                              // greater than the amount of time we want, then we update the game

                tick();// updating the game
                render();// rendering the game
                ticks++;// keeps track of the amount of time the game ticked
                delta--;// resets the delta variable
            }
            if (timer >= 1000000000) {// fps counter, every 9 billion nano seconds or one second, print out the frames
                                      // per second and reset to get an accurate fps count again
                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop(); // stopping the loop when running is false
    }

    public GameCamera getGameCamera() {// gameCamera accessor
        return gameCamera;
    }

    public int getWidth() {// accessor for width variable
        return width;
    }

    public int getHeight() {// accessor for height variable
        return height;
    }

    public KeyManager getKeyManager() {// accessor to get a key manager
        return keyManager;
    }

    public MouseManager getMouseManager(){
        return mouseManager;
    }

    public synchronized void start() {// method to start the thread/program for the game class
        // synchronized key word is used for threads, so nothing is messed up while
        // running
        if (running)
            return; // we don't want this to run while the game is already running, because we don't
                    // want to create a new thread for no reason and mess everything up

        thread = new Thread(this); // 'this' will run the game class on the thread that was just created
        thread.start(); // actually starts the thread, calls the run method
        running = true; // sets the running variable to true to turn on the game loop
    }

    public synchronized void stop() {// method to stop the thread/program for the game class
        if (!running) // we don't want to stop the game when it's already stopped to avoid
                      // complications
            return;
        running = false;
        try {
            thread.join();

        } catch (InterruptedException e) {
            e.printStackTrace(); // try catch statement to stop the program
        }
    }
}