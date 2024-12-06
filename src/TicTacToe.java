import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToe extends JFrame {
    private String player1, player2;
    private boolean vsComputer = false;
    private char currentPlayer = 'X';
    private JButton[][] buttons = new JButton[3][3];
    private JLabel statusLabel;

    public TicTacToe() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        JTextField player1Field = new JTextField(10);
        JTextField player2Field = new JTextField(10);
        JCheckBox computerCheckBox = new JCheckBox("Play vs Computer");
        JButton startButton = new JButton("Start Game");

        inputPanel.add(new JLabel("Player 1 (X): "));
        inputPanel.add(player1Field);
        inputPanel.add(new JLabel("Player 2 (O): "));
        inputPanel.add(player2Field);
        inputPanel.add(computerCheckBox);
        inputPanel.add(startButton);

        add(inputPanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                gamePanel.add(buttons[i][j]);
                int row = i, col = j;
                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));
            }
        }

        add(gamePanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Welcome to Tic-Tac-Toe!");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            player1 = player1Field.getText();
            player2 = player2Field.getText();
            vsComputer = computerCheckBox.isSelected();
            if (player1.isEmpty() || (player2.isEmpty() && !vsComputer)) {
                JOptionPane.showMessageDialog(this, "Please enter valid player names!");
            } else {
                if (vsComputer) player2 = "Computer";
                statusLabel.setText(player1 + "'s turn (X)");
                resetBoard();
            }
        });

        setFocusable(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });
    }

    private void handleButtonClick(int row, int col) {
        if (buttons[row][col].getText().isEmpty()) {
            buttons[row][col].setText(String.valueOf(currentPlayer));
            if (checkWin()) {
                statusLabel.setText((currentPlayer == 'X' ? player1 : player2) + " wins!");
                disableBoard();
            } else if (isBoardFull()) {
                statusLabel.setText("It's a draw!");
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                if (currentPlayer == 'X') {
                    statusLabel.setText(player1 + "'s turn (X)");
                } else {
                    statusLabel.setText(player2 + "'s turn (O)");
                    if (vsComputer) computerMove();
                }
            }
        }
    }

    private void handleKeyPress(int keyCode) {
        if (keyCode >= KeyEvent.VK_NUMPAD1 && keyCode <= KeyEvent.VK_NUMPAD9) {
            int num = keyCode - KeyEvent.VK_NUMPAD1;
            int row = num / 3;
            int col = num % 3;
            handleButtonClick(row, col);
        }
    }

    private void computerMove() {
        ArrayList<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        if (!emptyCells.isEmpty()) {
            Random rand = new Random();
            int[] move = emptyCells.get(rand.nextInt(emptyCells.size()));
            handleButtonClick(move[0], move[1]);
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][1].getText().equals(buttons[i][2].getText()) &&
                    !buttons[i][0].getText().isEmpty()) {
                return true;
            }
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[1][i].getText().equals(buttons[2][i].getText()) &&
                    !buttons[0][i].getText().isEmpty()) {
                return true;
            }
        }
        return (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().isEmpty()) ||
                (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                        buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                        !buttons[0][2].getText().isEmpty());
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableBoard() {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setEnabled(false);
            }
        }
    }

    private void resetBoard() {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setText("");
                button.setEnabled(true);
            }
        }
        currentPlayer = 'X';
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }
}
// acest joc a fost realizat in anul 3, in timpul laboratorului de ProiectareJava din cadrul UNSTPB