package app.ui;

import app.model.Computer;
import app.model.Position;
import app.vendor.Constant;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameUI extends JFrame {

    private JTextField txtPlayerWin, txtComputerWin, txtDraw, txtRound;
    private JButton btnNewGame;
    private JComboBox<Integer> cbGameIndex;
    private JPanel pnGameArea, pnScore, pnSelectedGame;

    private Position position = new Position();
    private Computer computer = new Computer();
    private int number = Constant.MINIMUM;
    private int round = 1;
    private int scorePlayer, scoreComputer , scoreDraw;

    public GameUI(String title) {
        super(title);
        addControls();
        this.txtDraw.setEnabled(false);
        this.txtComputerWin.setEnabled(false);
        this.txtPlayerWin.setEnabled(false);
        this.txtRound.setEnabled(false);
    }

    public GameUI(){
        this.number = Constant.MINIMUM;
        this.scoreComputer = 0;
        this.scorePlayer = 0;
        this.scoreDraw = 0;
    }

    private void addControls() {
        Container con = getContentPane();
        JPanel pnMain = new JPanel();
        pnGameArea = new JPanel();
        pnGameArea.setPreferredSize(new Dimension(300, 0));
        JPanel pnScoreAndNewGame = new JPanel();
        pnScoreAndNewGame.setPreferredSize(new Dimension(150, 0));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnScoreAndNewGame, pnGameArea);

        /*splitting pnScoreAndNewGame start*/
        pnScore = new JPanel();
        pnScore.setPreferredSize(new Dimension(100, 200));
        pnScore.setLayout(new BoxLayout(pnScore, BoxLayout.Y_AXIS));
        JPanel pnFeature = new JPanel();
        pnFeature.setPreferredSize(new Dimension(100, 0));
        pnFeature.setLayout(new BoxLayout(pnFeature, BoxLayout.Y_AXIS));
        JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnScore, pnFeature);
        pnScoreAndNewGame.setLayout(new BorderLayout());
        pnScoreAndNewGame.add(splitPaneLeft, BorderLayout.CENTER);
        /*splitting pnScoreAndNewGame end*/

        pnMain.setLayout(new BorderLayout());
        pnMain.add(splitPane, BorderLayout.CENTER);
        con.setLayout(new BorderLayout());
        con.add(pnMain);

        /*Game Area start*/
        pnGameArea.setLayout(new GridLayout(3, 3));
        this.addGameAreaUI(pnGameArea);
        /*Game Area end*/

        /*JPanel Score start*/
        Border borderScore = BorderFactory.createLineBorder(Color.BLUE);
        TitledBorder titleBorderScore = new TitledBorder(borderScore, "Score");
        titleBorderScore.setTitleJustification(TitledBorder.CENTER);
        titleBorderScore.setTitleColor(Color.RED);
        pnScore.setBorder(titleBorderScore);
        this.addScoreAreaUI(pnScore);
        /*JPanel Score end*/

        /*Select start*/
        pnSelectedGame = new JPanel();
        pnSelectedGame.setPreferredSize(new Dimension(100, 200));
        pnSelectedGame.setLayout(new BoxLayout(pnSelectedGame, BoxLayout.Y_AXIS));
        this.addFeatureUI(pnSelectedGame);
        pnFeature.add(pnSelectedGame);
        /*select end*/

        JPanel pnNewGame = new JPanel();
        pnNewGame.setLayout(new FlowLayout());
        btnNewGame = new JButton("New game");
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGameAction();
            }
        });
        pnNewGame.add(btnNewGame);
        pnFeature.add(pnNewGame);
    }

    private void addFeatureUI(JPanel pnSelectedGame) {
        JPanel pnGameIndex = new JPanel();
        pnGameIndex.setLayout(new FlowLayout());
        JLabel lblGameIndex = new JLabel("Select:");
        cbGameIndex = new JComboBox<Integer>();
        cbGameIndex.setPreferredSize(new Dimension(50, 20));
        for (int i = 0; i < 5; i++) {
            cbGameIndex.addItem(2*i + 1);
        }
        cbGameIndex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                number = (int) cbGameIndex.getSelectedItem();
            }
        });
        pnGameIndex.add(lblGameIndex);
        pnGameIndex.add(cbGameIndex);
        pnSelectedGame.add(pnGameIndex);

        JPanel pnRound = new JPanel();
        pnRound.setLayout(new FlowLayout());
        JLabel lblRound = new JLabel("Round:");
        txtRound = new JTextField(5);
        txtRound.setText(String.valueOf(round));
        this.txtRound.setEnabled(false);
        pnRound.add(lblRound);
        pnRound.add(txtRound);
        pnSelectedGame.add(pnRound);
        lblGameIndex.setPreferredSize(lblRound.getPreferredSize());
    }

    private void addScoreAreaUI(JPanel panel) {
        JPanel pnPlayerWin = new JPanel();
        pnPlayerWin.setLayout(new FlowLayout());
        JLabel lblPlayer = new JLabel("Player:");
        txtPlayerWin = new JTextField(5);
        txtPlayerWin.setText(String.valueOf(scorePlayer));
        this.txtPlayerWin.setEnabled(false);
        pnPlayerWin.add(lblPlayer);
        pnPlayerWin.add(txtPlayerWin);
        panel.add(pnPlayerWin);

        JPanel pnDraw = new JPanel();
        pnDraw.setLayout(new FlowLayout());
        JLabel lblDraw = new JLabel("Draw:");
        txtDraw = new JTextField(5);
        txtDraw.setText(String.valueOf(scoreDraw));
        txtDraw.setEnabled(false);
        pnDraw.add(lblDraw);
        pnDraw.add(txtDraw);
        panel.add(pnDraw);

        JPanel pnComputerWin = new JPanel();
        pnComputerWin.setLayout(new FlowLayout());
        JLabel lblComputer = new JLabel("Computer:");
        txtComputerWin = new JTextField(5);
        txtComputerWin.setText(String.valueOf(scoreComputer));
        this.txtComputerWin.setEnabled(false);
        pnComputerWin.add(lblComputer);
        pnComputerWin.add(txtComputerWin);
        panel.add(pnComputerWin);

        lblPlayer.setPreferredSize(lblComputer.getPreferredSize());
        lblDraw.setPreferredSize(lblComputer.getPreferredSize());
    }

    private void addGameAreaUI(JPanel panel) {
        final JButton[] buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            final int idx = i;
            JButton button = new JButton();
            buttons[i] = button;
            button.setPreferredSize(new Dimension(100, 100));
            button.setOpaque(true);
            button.setFont(new Font(null, Font.PLAIN, 100));
            button.addMouseListener(new MouseListener() {
                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    button.setEnabled(false);
                    button.setText("" + position.getTurn());
                    move(idx);
                    if (!position.gameEnd()) {
                        computer.bestMove(position);
                        int best = computer.getBestTurn();
                        buttons[best].setEnabled(false);
                        buttons[best].setText("" + position.getTurn());
                        move(best);
                    }
                    if (position.gameEnd()) {
                        String message = "";
                        if (position.win(Constant.X)) {
                            message = "You won!";
                            scorePlayer++;
                        } else if (position.win(Constant.O)) {
                            message = "Computer won!";
                            scoreComputer++;
                        } else {
                            message = "Draw!";
                            scoreDraw++;
                        }
                        JOptionPane.showMessageDialog(null, message);
                        continueGame();
                        if (round == number + 1) {
                            if (scorePlayer - scoreComputer > 0){
                                message = "Congratulations, You win!";
                            } else if (scorePlayer - scoreComputer < 0) {
                                message = "Too bad, Computer win!";
                            } else {
                                message = "Uhmm, Draw!";
                            }
                            JOptionPane.showMessageDialog(null, message);
                            newGameAction();
                        }
                    }
                }
            });
            panel.add(button);
            panel.setVisible(true);
        }
    }

    private void continueGame() {
        round+=1;
        txtPlayerWin.setText(String.valueOf(scorePlayer));
        txtComputerWin.setText(String.valueOf(scoreComputer));
        txtDraw.setText(String.valueOf(scoreDraw));
        txtRound.setText(String.valueOf(round));
        txtRound.setEnabled(false);
        position = position.reset();
        this.pnGameArea.removeAll();
        this.addGameAreaUI(pnGameArea);
        revalidate();
        repaint();
    }

    private void move(int idx) {
        position = position.move(idx);
    }

    private void newGameAction() {
        //System.out.println("new game button active!");
        //new GameUI();
        setDefaultValue();
        position = position.reset();
        this.pnGameArea.removeAll();
        this.pnScore.removeAll();
        this.pnSelectedGame.removeAll();
        this.addGameAreaUI(pnGameArea);
        this.addScoreAreaUI(pnScore);
        this.addFeatureUI(pnSelectedGame);
        revalidate();
        repaint();
    }

    private void setDefaultValue() {
        scoreDraw = 0;
        scorePlayer = 0;
        scoreComputer = 0;
        round = 1;
    }

    public void showWindow() {
        this.setSize(500, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
