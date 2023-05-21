import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;


public class Pong extends PApplet {

    public static void main(String[] args){

        PApplet.main("Pong");
    }

    PFont myFont;
    PFont mySecondFont;
    PImage crown;

    // All variables declaration
    float ball_x, ball_y, ball_radius, ball_speed_x, ball_speed_y;
    float left_paddle_x, left_paddle_y, left_paddle_width, left_paddle_height;
    float right_paddle_x, right_paddle_y, right_paddle_width, right_paddle_height;
    float paddle_speed;
    int score_left, score_right, win_score, color_red, color_green;
    boolean up_left, down_left, up_right, down_right, paused;


    public void settings (){
          size(800,600);
    }

    public void setup(){
        background(0);
        noStroke();
        ball_x = width/2f;
        ball_y = height/2f;
        ball_radius = 15;
        ball_speed_x = 0;
        ball_speed_y = 0;
        rectMode(CENTER);
        textAlign(CENTER, CENTER);
        left_paddle_x = 10;
        left_paddle_y = height/2f;
        left_paddle_width = 15;
        left_paddle_height = 110;
        right_paddle_x = width - 10;
        right_paddle_y = height/2f;
        right_paddle_width = 15;
        right_paddle_height = 110;
        paddle_speed = 7;
        score_left = 0;
        score_right = 0;
        win_score = 10;
        color_green = 0;
        color_red = 0;
        crown = loadImage("crown.png");

    }

    public void draw(){
        background(0);
        draw_ball();
        draw_paddle();
        bounce_ball();
        move_paddle();
        check_paddle_border_distance();
        check_ball_paddle_distance();
        score_caption();
        draw_middle_line();
        game_over();
        instruction();
    }

    // This function draw ball with white color
    public void draw_ball(){
        fill(255);
        circle(ball_x, ball_y, 2*ball_radius);
    }

    /* This function makes ball move and bounce inside of canvas, also calculates scores for each player and after every score gives ball start position.
    In this game start condition of paddles is black. For every point gets the paddle a dose color of their team. For example left side paddle gets
    one dose red color and right side - one dose green color. The goal is to get ten doses to be perfect Red or Green.
     */
    public void bounce_ball(){
        ball_x = ball_x + ball_speed_x;
        if (ball_x + ball_radius > width) {
            score_left = score_left + 1;
            color_red = color_red + 25;
            ball_x = width/2f;
            ball_y = height/2f;
            ball_speed_x = - ball_speed_x;
        }
        if(ball_x - ball_radius < 0){
            score_right = score_right + 1;
            color_green = color_green + 25;
            ball_speed_x = - ball_speed_x;
            ball_x = width/2f;
            ball_y = height/2f;
        }

        ball_y =  ball_y + ball_speed_y;
        if ((ball_y + ball_radius > height) || (ball_y - ball_radius < 0)) {
            ball_speed_y = - ball_speed_y;
        }

    }

    // This function draws two paddles for two players with different colors
    public void draw_paddle(){
        stroke(255, 0, 0);
        fill(color_red, 0, 0);
        rect(left_paddle_x, left_paddle_y, left_paddle_width, left_paddle_height);
        stroke(0, 255, 0);
        fill(0, color_green, 0);
        rect(right_paddle_x, right_paddle_y, right_paddle_width, right_paddle_height);
    }

    /*This function initialised some keys from keyboard. When we press it, some function is called (this time move paddles and pause)
    There are lowercase and uppercase letters in case someone has Caps Lock on. Also I used z and y for down left move,
    because English and German keyboard are different and if someone uses German for them z is y.  */
    public void keyPressed(){
        if(key == 'a' || key == 'A'){
                up_left = true;
        }
        if(key == 'z' || key == 'Z' || key == 'y' || key == 'Y'){
                down_left = true;
        }
        if(key == 'k' || key == 'K'){
                up_right = true;
        }
        if(key == 'm' || key == 'M'){
                down_right = true;
        }
        if (key == ' '){
            paused = !paused;
            if(paused){
                noLoop();
            }else{
                loop();
            }
        }

    }

    public void keyReleased(){
        if(key == 'a' || key== 'A'){
            up_left = false;
        }
        if(key == 'z' || key== 'Z' ||  key == 'y' || key == 'Y'){
            down_left = false;
        }
        if(key == 'k' || key== 'K'){
            up_right = false;
        }
        if(key == 'm' || key== 'M'){
            down_right = false;
        }


    }


    // This function moves paddles up and down with help of keys, which are initialised in KeyPressed and KeyReleased functions.
    public void move_paddle(){
        if(up_left){
            left_paddle_y = left_paddle_y - paddle_speed;
        }
        if (down_left){
            left_paddle_y= left_paddle_y + paddle_speed;
        }
        if(up_right){
            right_paddle_y = right_paddle_y - paddle_speed;
        }
        if (down_right){
            right_paddle_y= right_paddle_y + paddle_speed;
        }

    }

    // This function checks distance from paddles to upper and lower border and stops it if paddle is too near
    public void check_paddle_border_distance(){
        if(left_paddle_y - left_paddle_height/2 < 0){
            left_paddle_y = left_paddle_y + paddle_speed;
        }
        if(left_paddle_y + left_paddle_height/2 > height){
            left_paddle_y = left_paddle_y - paddle_speed;
        }
        if(right_paddle_y - right_paddle_height/2 < 0){
            right_paddle_y = right_paddle_y + paddle_speed;
        }
        if(right_paddle_y + right_paddle_height/2 > height){
            right_paddle_y = right_paddle_y - paddle_speed;
        }
    }

    // This function checks distance from ball to paddles, when ball hits the paddle, it reverses the direction and changes the speed
    public void check_ball_paddle_distance(){
        if(ball_x - ball_radius < left_paddle_x + left_paddle_width/2 && ball_y - ball_radius < left_paddle_y + left_paddle_height/2 && ball_y + ball_radius > left_paddle_y - left_paddle_height/2 ){
            if (ball_speed_x < 0) {
                ball_speed_x = - ball_speed_x;
                ball_speed_x = random(4, 6);
                ball_speed_y = random(4, 6);
            }

        }
        if(ball_x + ball_radius > right_paddle_x - right_paddle_width/2 && ball_y - ball_radius < right_paddle_y + right_paddle_height/2 && ball_y + ball_radius > right_paddle_y - right_paddle_height/2 ) {
            if (ball_speed_x > 0) {
                ball_speed_x = random(4, 6);
                ball_speed_y = random(4, 6);
                ball_speed_x = -ball_speed_x;

            }
        }
    }
    
    // This function is for score font, colors and positions.
    public void score_caption(){
        myFont = createFont("Arcade", 60);
        textFont(myFont);
        fill(255, 0, 0);
        textAlign(LEFT);
        text(score_left, width/2f - 100, 60);
        fill(0, 255, 0);
        textAlign(RIGHT);
        text(score_right, width/2f + 100, 60);
    }

    // This Function is for game over caption. After one player wins, ball stops bouncing. After clicking mouse, play will start over.
    public void game_over(){
        textAlign(CENTER, CENTER);
        if (score_left == win_score){
            ball_speed_x = 0;
            ball_speed_y = 0;
            rect(width/2f, height/2f, 400, 60);
            fill(255, 0, 0);
            text("Team Red wins", width/2f, height/2f);
            mySecondFont = createFont("Data Control", 15);
            textFont(mySecondFont);
            fill(255);
            text("Click mouse to play again", width/2f, height/2f + 50);
            image(crown, width/2f - 40, height/2f - 64, crown.width/9f, crown.height/9f);
        }
         if (score_right == win_score){
             ball_speed_x = 0;
             ball_speed_y = 0;
             rect(width/2f, height/2f, 450, 60);
             fill(0, 255, 0);
             text("Team Green wins", width/2f, height/2f);
             fill(255);
             mySecondFont = createFont("Data Control", 15);
             textFont(mySecondFont);
             text("Click mouse to play again", width/2f, height/2f + 50);
             image(crown, width/2f - 40, height/2f - 64, crown.width/9f, crown.height/9f);
         }

         if(mousePressed){
             score_left = 0;
             score_right = 0;
             ball_speed_x = random(4, 6);
             ball_speed_y = random (3,5);
         }
    }
    // This function draws a dashed line in the middle of "playground"
    public void draw_middle_line(){
        for (int i = 0; i < 630; i=i+20) {
            fill(255);
            noStroke();
            rect(width/2f, i, 10, 10);
        }
    }

    // This function is for the first "page", when player starts, at first he/she reads infos about key shortcuts.
    public void instruction(){
        if(ball_speed_x == 0 && ball_speed_y == 0 && score_right == 0 && score_left == 0){
            fill(255);
            rect(width/2f, height/2f, 400,200);
            mySecondFont = createFont("Data Control", 15);
            textFont(mySecondFont);
            textAlign(CENTER, CENTER);
            fill(0);
            text("Instruction", width/2f, 230);
            text("For Start click the mouse", width/2f, 260);
            text("Team Red: key 'A' - up, key 'Z' - down", width/2f, 280);
            text("Team Green: key 'K' - up, key 'M' - down", width/2f, 300);
            text("For Pause/Resume press spacebar", width/2f, 350);

        }
    }

}