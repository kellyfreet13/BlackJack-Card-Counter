import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Kelly on 3/2/2017.
 * Nord VPN Servers:
 *  LA # 12, 21, 318 - 320, 326, 357 - 360,
 *  405, 406, 409, 410, 427 - 434, 459 - 466,
 *  483 - 490
 */
public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener, ActionListener{

    private final int num_cards_in_deck = 52;
    private final int num_cards_in_six_decks = 6*num_cards_in_deck;
    private final int num_cards_in_eight_decks = 8*num_cards_in_deck;

    private final int num_aces_in_six_decks = 24;
    private final int num_aces_in_eight_decks = 32;

    private final int num_eights_in_six_decks = 24;
    private final int num_eights_in_eight_decks = 32;

    private final int num_nines_in_six_decks = 24;
    private final int num_nines_in_eight_decks = 32;

    private double bust_chance;

    private double two_percentage;
    private double three_percentage;
    private double four_percentage;
    private double five_percentage;
    private double six_percentage;
    private double seven_percentage;
    private double eight_percentage;
    private double nine_percentage;
    private double ten_percentage;
    private double ace_percentage;

    private int two_count = 0;
    private int three_count = 0;
    private int four_count = 0;
    private int five_count = 0;
    private int six_count = 0;
    private int seven_count = 0;
    private int eight_count = 0;
    private int nine_count = 0;
    private int ten_count = 0;
    private int ace_count = 0;

    private int num_single_card = 4 * 8;
    private int num_tens = 4 * 4 * 8;

    private int hi_lo_count;
    private int hi_opt_II_count;
    private float halves_count;

    private JLabel hi_lo_count_label;
    private JLabel hi_opt_II_count_label;
    private JLabel halves_count_label;

    //determine what these are later
    private final int hi_lo_initial_count = 0;
    private final int hi_opt_II_initial_count = 0;
    private final int halves_initial_count = 0;

    private final int[] hi_lo_arr = new int[10];
    private final int[] hi_opt_II_arr = new int[10];
    private final double[] halves_arr = new double[10];

    //this will be used to calculate an accurate true count
    private int num_cards_dealt = 0;

    //should probably abstract this, but it's more effort than
    //it's worth. Will be using true count with 6 decks
    private double hi_lo_true_count;
    private double hi_opt_II_true_count ;
    private double halves_true_count;

    private JLabel hi_lo_true_count_label;
    private JLabel hi_opt_II_true_count_label;
    private JLabel halves_true_count_label;
    
    private JButton two;
    private JButton three;
    private JButton four;
    private JButton five;
    private JButton six;
    private JButton seven;
    private JButton eight;
    private JButton nine;
    private JButton ten;
    private JButton ace;

    private JButton reset;

    private int baseBet = 10;
    private int hi_lo_betMultiplier;
    private int hi_opt_betMultiplier;
    private int halves_betMultiplier;

    private int hi_lo_true_count_bet;
    private int hi_opt_true_count_bet;
    private int halves_true_count_bet;

    private int hi_lo_true_count_round;
    private int hi_opt_true_count_round;
    private int halves_true_count_round;

    public Panel(){
        GridLayout gl = new GridLayout(6, 3);
        setLayout(gl);

        initializeCounts();
        populateBettingArrays();
        populateGrid();
    }

    public void populateGrid(){
        hi_lo_count_label = new JLabel("Hi-Lo Count");
        hi_opt_II_count_label = new JLabel("Hi-Opt-II Count");
        halves_count_label = new JLabel("Halves Count");

        hi_lo_true_count_label = new JLabel("Hi-Lo true count");
        hi_opt_II_true_count_label = new JLabel("Hi-Opt-II count");
        halves_true_count_label = new JLabel("Halves true count");

        two = new JButton("2");
        three = new JButton("3");
        four = new JButton("4");
        five = new JButton("5");
        six = new JButton("6");
        seven = new JButton("7");
        eight = new JButton("8");
        nine = new JButton("9");
        ten = new JButton("10");
        ace = new JButton("A");

        two.setFont(new Font("Arial", Font.BOLD, 40));
        three.setFont(new Font("Arial", Font.BOLD, 40));
        four.setFont(new Font("Arial", Font.BOLD, 40));
        five.setFont(new Font("Arial", Font.BOLD, 40));
        six.setFont(new Font("Arial", Font.BOLD, 40));
        seven.setFont(new Font("Arial", Font.BOLD, 40));
        eight.setFont(new Font("Arial", Font.BOLD, 40));
        nine.setFont(new Font("Arial", Font.BOLD, 40));
        ten.setFont(new Font("Arial", Font.BOLD, 40));
        ace.setFont(new Font("Arial", Font.BOLD, 40));

        reset = new JButton("reset");
        reset.setBackground(new Color(244, 78, 66));

        this.add(hi_lo_count_label);
        this.add(hi_opt_II_count_label);
        this.add(halves_count_label);

        this.add(hi_lo_true_count_label);
        this.add(hi_opt_II_true_count_label);
        this.add(halves_true_count_label);

        this.add(two);
        this.add(three);
        this.add(four);
        this.add(five);
        this.add(six);
        this.add(seven);
        this.add(eight);
        this.add(nine);
        this.add(ten);
        this.add(ace);

        this.add(reset);

        addListeners();
    }

    public void initializeCounts(){
        //set the current count to initial count when
        //window is instantiated
        hi_lo_count = hi_lo_initial_count;
        hi_opt_II_count = hi_opt_II_initial_count;
        halves_count = halves_initial_count;
    }

    public void populateBettingArrays(){
        populateHiLoArr();
        populateHiOptIIArr();
        populateHalvesArr();
    }

    public void populateHiLoArr(){
        hi_lo_arr[0] = 1;
        hi_lo_arr[1] = 1;
        hi_lo_arr[2] = 1;
        hi_lo_arr[3] = 1;
        hi_lo_arr[4] = 1;
        hi_lo_arr[5] = 0;
        hi_lo_arr[6] = 0;
        hi_lo_arr[7] = 0;
        hi_lo_arr[8] = -1;
        hi_lo_arr[9] = -1;
    }

    public void populateHiOptIIArr(){
        hi_opt_II_arr[0] = 1;
        hi_opt_II_arr[1] = 1;
        hi_opt_II_arr[2] = 2;
        hi_opt_II_arr[3] = 2;
        hi_opt_II_arr[4] = 1;
        hi_opt_II_arr[5] = 1;
        hi_opt_II_arr[6] = 0;
        hi_opt_II_arr[7] = 0;
        hi_opt_II_arr[8] = -2;
        hi_opt_II_arr[9] = 0;
    }

    public void populateHalvesArr(){
        halves_arr[0] = .5;
        halves_arr[1] = 1;
        halves_arr[2] = 1;
        halves_arr[3] = 1.5;
        halves_arr[4] = 1;
        halves_arr[5] = .5;
        halves_arr[6] = 0;
        halves_arr[7] = -.5;
        halves_arr[8] = -1;
        halves_arr[9] = -1;

    }

    public void addListeners(){
        two.addMouseListener(this);
        three.addMouseListener(this);
        four.addMouseListener(this);
        five.addMouseListener(this);
        six.addMouseListener(this);
        seven.addMouseListener(this);
        eight.addMouseListener(this);
        nine.addMouseListener(this);
        ten.addMouseListener(this);
        ace.addMouseListener(this);
        reset.addMouseListener(this);

        two.addActionListener(this);
        three.addActionListener(this);
        four.addActionListener(this);
        five.addActionListener(this);
        six.addActionListener(this);
        seven.addActionListener(this);
        eight.addActionListener(this);
        nine.addActionListener(this);
        ten.addActionListener(this);
        ace.addActionListener(this);
        reset.addActionListener(this);

        addMouseListener(this);
        addKeyListener(this);
    }

    public void setBettingMultiplier(){
        hi_lo_true_count_round = (int) Math.round(hi_lo_true_count);
        hi_opt_true_count_round = (int) Math.round(hi_opt_II_true_count);
        halves_true_count_round = (int) Math.round(halves_true_count);

        Color cNeg5 = new Color(255, 0, 0);
        Color cNeg4 = new Color(255, 38, 0);
        Color cNeg3 = new Color(255, 106, 0);
        Color cNeg2 = new Color(255, 153, 0);
        Color cNeg1 = new Color(255, 242, 132);
        Color c0 = new Color(255, 255, 255);
        Color c1 = new Color(140, 255, 235);
        Color c2 = new Color(69, 160, 239);
        Color c3 = new Color(106, 242, 174);
        Color c4 = new Color(76, 247, 122);
        Color c5 = new Color(8, 255, 0);

        if(hi_lo_true_count_round <= 0){
            hi_lo_betMultiplier = 1;
            hi_lo_true_count_label.setBackground(c0);
            if(hi_lo_true_count_round <= -11){
                hi_lo_true_count_label.setBackground(cNeg5);
            } else if (hi_lo_true_count_round <= -9){
                hi_lo_true_count_label.setBackground(cNeg4);
            } else if (hi_lo_true_count_round <= -7){
                hi_lo_true_count_label.setBackground(cNeg3);
            } else if (hi_lo_true_count_round <= -5){
                hi_lo_true_count_label.setBackground(cNeg2);
            } else if (hi_lo_true_count_round <= -3){
                hi_lo_true_count_label.setBackground(cNeg1);
            }
        } else if(hi_lo_true_count_round == 1){
            hi_lo_betMultiplier = 1;
            hi_lo_true_count_label.setBackground(c1);
        } else if (hi_lo_true_count_round == 2 || hi_lo_true_count_round == 3){
            hi_lo_betMultiplier = 2;
            hi_lo_true_count_label.setBackground(c2);
        } else if (hi_lo_true_count_round == 4 || hi_lo_true_count_round == 5){
            hi_lo_betMultiplier = 3;
            hi_lo_true_count_label.setBackground(c3);
        } else if (hi_lo_true_count_round == 6 || hi_lo_true_count_round == 7){
            hi_lo_betMultiplier = 4;
            hi_lo_true_count_label.setBackground(c4);
        } else if (hi_lo_true_count_round >= 8){
            hi_lo_betMultiplier = 5;
            hi_lo_true_count_label.setBackground(c5);
        }

        if(hi_opt_true_count_round <= 0){
            hi_opt_betMultiplier = 1;
            hi_opt_II_true_count_label.setBackground(c0);
            if(hi_opt_true_count_round <= -11){
                hi_opt_II_true_count_label.setBackground(cNeg5);
            } else if (hi_opt_true_count_round <= -9){
                hi_opt_II_true_count_label.setBackground(cNeg4);
            } else if (hi_opt_true_count_round <= -7){
                hi_opt_II_true_count_label.setBackground(cNeg3);
            } else if (hi_opt_true_count_round <= -5){
                hi_opt_II_true_count_label.setBackground(cNeg2);
            }else if (hi_opt_true_count_round <= -3){
                hi_opt_II_true_count_label.setBackground(cNeg1);
            }
        } else if(hi_opt_true_count_round == 1){
            hi_opt_betMultiplier = 1;
            hi_opt_II_true_count_label.setBackground(c1);
        } else if (hi_opt_true_count_round == 2 || hi_opt_true_count_round == 3){
            hi_opt_betMultiplier = 2;
            hi_opt_II_true_count_label.setBackground(c2);
        } else if (hi_opt_true_count_round == 4 || hi_opt_true_count_round == 5){
            hi_opt_betMultiplier = 3;
            hi_opt_II_true_count_label.setBackground(c3);
        } else if (hi_opt_true_count_round == 6 || hi_opt_true_count_round == 7){
            hi_opt_betMultiplier = 4;
            hi_opt_II_true_count_label.setBackground(c4);
        } else if (hi_opt_true_count_round >= 8){
            hi_opt_betMultiplier = 5;
            hi_opt_II_true_count_label.setBackground(c5);
        }

        if(halves_true_count_round <= 0){
            halves_betMultiplier = 1;
            if(halves_true_count_round <= -11){
                halves_true_count_label.setBackground(cNeg5);
            } else if (halves_true_count_round <= -9){
                halves_true_count_label.setBackground(cNeg4);
            } else if (halves_true_count_round <= -7){
                halves_true_count_label.setBackground(cNeg3);
            } else if (halves_true_count_round <= -5){
                halves_true_count_label.setBackground(cNeg2);
            } else if (halves_true_count_round <= -3){
                halves_true_count_label.setBackground(cNeg1);
            }
        } else if(halves_true_count_round == 1){
            halves_betMultiplier = 1;
            halves_true_count_label.setBackground(c0);
        } else if (halves_true_count_round == 2 || halves_true_count_round == 5){
            halves_betMultiplier = 2;
            halves_true_count_label.setBackground(c2);
        } else if (halves_true_count_round == 6 || halves_true_count_round == 9){
            halves_betMultiplier = 3;
            halves_true_count_label.setBackground(c3);
        } else if (halves_true_count_round >= 9){
            halves_betMultiplier = 4;
            halves_true_count_label.setBackground(c5);
        }

        hi_lo_true_count_bet = baseBet * hi_lo_betMultiplier;
        hi_opt_true_count_bet = baseBet * hi_opt_betMultiplier;
        halves_true_count_bet = baseBet * halves_betMultiplier;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        hi_lo_count_label.setOpaque(true);
        hi_opt_II_count_label.setOpaque(true);
        halves_count_label.setOpaque(true);

        hi_lo_true_count_label.setOpaque(true);
        hi_opt_II_true_count_label.setOpaque(true);
        halves_true_count_label.setOpaque(true);

        num_cards_dealt++;

        hi_lo_true_count =
                (double)(hi_lo_count) / (8 * ((double)(num_cards_in_eight_decks - num_cards_dealt) / (double) num_cards_in_eight_decks));
        hi_opt_II_true_count =
                (double)(hi_opt_II_count) / (8 * ((double)(num_cards_in_eight_decks - num_cards_dealt) / (double) num_cards_in_eight_decks));
        halves_true_count =
                (double) halves_count / (8 * ((double)(num_cards_in_eight_decks - num_cards_dealt) / (double) num_cards_in_eight_decks));

        setBettingMultiplier();

        if(e.getSource() == two){
            two_count++;
            two_percentage = (double)(num_single_card - two_count) /(double) num_single_card;
            updateTextLabels(0);
        } else if(e.getSource() == three){
            three_count++;
            three_percentage = (double)(num_single_card - three_count) /(double) num_single_card;
            updateTextLabels(1);
        } else if(e.getSource() == four){
            four_count++;
            four_percentage = (double)(num_single_card - four_count) /(double) num_single_card;
            updateTextLabels(2);
        } else if(e.getSource() == five){
            five_count++;
            five_percentage = (double)(num_single_card - five_count) /(double) num_single_card;
            updateTextLabels(3);
        } else if(e.getSource() == six){
            six_count++;
            six_percentage = (double)(num_single_card - six_count) /(double) num_single_card;
            updateTextLabels(4);
        } else if(e.getSource() == seven){
            seven_count++;
            seven_percentage = (double)(num_single_card - seven_count) /(double) num_single_card;
            updateTextLabels(5);
        } else if(e.getSource() == eight){
            eight_count++;
            eight_percentage = (double)(num_single_card - eight_count) /(double) num_single_card;
            updateTextLabels(6);
        } else if(e.getSource() == nine){
            nine_count++;
            nine_percentage = (double)(num_single_card - nine_count) /(double) num_single_card;
            updateTextLabels(7);
        } else if(e.getSource() == ten){
            ten_count++;
            ten_percentage = (double)(num_tens - ten_count) /(double) num_tens;
            updateTextLabels(8);
        } else if(e.getSource() == ace){
            ace_count++;
            ace_percentage = (double)(num_single_card - ace_count) /(double) num_single_card;
            updateTextLabels(9);
        } else if(e.getSource() == reset){
            reset();
        }
    }

    public void reset(){
        num_cards_dealt = 0;
        ace_count = 0;
        eight_count = 0;
        nine_count = 0;

        hi_lo_count = hi_lo_initial_count;
        hi_opt_II_count = hi_opt_II_initial_count;
        halves_count = halves_initial_count;

        //these might be different as well
        hi_lo_true_count = 0;
        hi_opt_II_true_count = 0;
        halves_true_count = 0;

        hi_lo_count_label.setText("Hi-Lo Count");
        hi_opt_II_count_label.setText("Hi-Opt-II Count");
        halves_count_label.setText("Halves Count");

        hi_lo_true_count_label.setText("Hi-Lo true count");
        hi_opt_II_true_count_label.setText("Hi-Opt-II count");
        halves_true_count_label.setText("Halves true count");
    }

    public void updateTextLabels(int bettingIndex){
        hi_lo_count += hi_lo_arr[bettingIndex];
        hi_opt_II_count += hi_opt_II_arr[bettingIndex];
        halves_count += halves_arr[bettingIndex];

        hi_lo_count_label.setText("Hi-Lo Count: "+hi_lo_count);
        hi_opt_II_count_label.setText("Hi-Opt Count: "+hi_opt_II_count);
        halves_count_label.setText("Halves Count: "+halves_count);

        String hi_lo_output_0 = String.format("%.4g",hi_lo_true_count);
        String hi_lo_output_1 = "Bet: " + hi_lo_true_count_bet;
        String hi_opt_output_0 = String.format("%.4g",hi_opt_II_true_count);
        String hi_opt_output_1 = "Bet: " + hi_opt_true_count_bet;
        String halves_output_0 = String.format("%.4g",halves_true_count);
        String halves_output_1 = "Bet: " + halves_true_count_bet;

        String hi_lo_total_output = "<html>"+hi_lo_output_0+"<br>"+hi_lo_output_1+"</html>";
        String hi_opt_total_output = "<html>"+hi_opt_output_0+"<br>"+hi_opt_output_1;
        String halves_total_output = "<html>"+halves_output_0+"<br>"+halves_output_1+"</html>";

        String aces_remaining = "<br>Aces remaining: " +
                (num_aces_in_eight_decks-ace_count);
        String eights_remaining = "<br>Eights remaining: " +
                (num_eights_in_eight_decks-eight_count);
        String nines_remaining = "<br>Nines remaining: " +
                (num_nines_in_eight_decks - nine_count)+ "</html>";

        hi_lo_true_count_label.setText(hi_lo_total_output);
        hi_opt_II_true_count_label.setText(hi_opt_total_output + aces_remaining
            + eights_remaining + nines_remaining);
        halves_true_count_label.setText(halves_total_output);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {

    }
}
