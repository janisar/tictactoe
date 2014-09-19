import java.util.Scanner;

public class Program {
	static int[] m = new int[9];
	static int[] cpuPreferedMoves = new int[] { 4, 0, 2, 6, 8, 1, 3, 5, 7 };
	static int[][] possibleWins = new int[][] { { 0, 2, 1 }, { 4, 3, 5 },
		{ 6, 8, 7 }, // ROWS
		{ 0, 6, 3 }, { 4, 1, 7 }, { 2, 8, 5 }, // COLUMNS
		{ 4, 0, 8 }, { 4, 2, 6 } }; // DIAGONALS
	private static final int SQUARES_IN_ROW = 3;
	private static final int PLAYER = 1;
	private static final int EMPTY = 0;
	private static final int CPU = -1;

	public static void main(String args[]) {
		showBoard();
		playGame();
	}

	private static void playGame() {
		Scanner scanner = new Scanner(System.in);
		while (boardHasEmptySquare()) {
			userTurn(scanner);
			showCurrentBoard();
			checkWin(m);
			if (boardHasEmptySquare()) {
				cpuTurn();
				showCurrentBoard();
				checkWin(m);
			}
		}
		scanner.close();
	}

	private static void showCurrentBoard() {
		for (int i = 0; i < m.length; i += SQUARES_IN_ROW) {
			System.out.print("+---+---+---+\n");
			System.out.print("| ");
			printSquare(m[i]);
			System.out.print(" | ");
			printSquare(m[i + 1]);
			System.out.print(" | ");
			printSquare(m[i + 2]);
			System.out.println(" | ");
		}
		System.out.println("+---+---+---+");
	}

	private static void printSquare(int i) {
		if (i == -1) {
			System.out.print("O");
		} else if (i == 1) {
			System.out.print("X");
		} else {
			System.out.print(" ");
		}
	}

	private static void cpuTurn() {
		System.out.println("Arvuti kord: ");
		int cpuMove = makeMove(m);
		m[cpuMove] = CPU;
	}

	private static void userTurn(Scanner scanner) {
		System.out.println("Make your move.");
		int userMove = readInput(scanner);
		m[userMove] = PLAYER;
	}

	private static boolean boardHasEmptySquare() {
		for (int i : m) {
			if (i == EMPTY) {
				return true;
			}
		}
		return false;
	}

	public static int readInput(Scanner scanner) {
		int userMove = -1;
		boolean correctMove = false;
		while (!correctMove) {
			String userMoveString = scanner.next();
			try {
				userMove = Integer.valueOf(userMoveString);
				correctMove = true;
				if (userMove > 9 || userMove < 0) {
					System.out
					.println("Out of bounds. Choose a number from 0-8");
					correctMove = false;
				} else if (m[userMove] != 0) {
					System.out.println("That square has been filled.");
					correctMove = false;
				}
			} catch (NumberFormatException ex) {
				System.out
				.println("That was not a number. Please choose a number from 0-8.");
			}
		}
		return userMove;
	}

	public static int makeMove(int[] board) {
		int result = -1;
		int bestRow = 0;
		for (int i = 0; i < possibleWins.length; i++) {
			int currentResult = 0;
			boolean canMove = false;
			for (int j = 0; j < possibleWins[i].length; j++) {
				if (m[possibleWins[i][j]] == PLAYER) {
					currentResult -= 3;
				} else if (m[possibleWins[i][j]] == CPU) {
					currentResult += 4;
				} else {
					canMove = true;
				}
			}
			if (canMove && (Math.pow(currentResult, 2) >= Math.pow(bestRow, 2))) {
				bestRow = currentResult;
				result = calculateRow(possibleWins[i]);
			}
		}
		return result;
	}

	private static int calculateRow(int[] row) {
		for (int i : row) {
			if (m[i] == EMPTY) {
				return i;
			}
		}
		return -1;
	}

	public static void checkWin(int[] board) {
		int win = 0;
		for (int i = 0; i < possibleWins.length; i++) {
			for (int j = 0; j < possibleWins[i].length; j++) {
				if (m[possibleWins[i][j]] == CPU) {
					win++;
				} else if (m[possibleWins[i][j]] == PLAYER) {
					win--;
				}
			}
			if (win == -3) {
				System.out.println("Sinu võit");
				System.exit(1);
			} else if (win == 3) {
				System.out.println("Arvuti võit");
				System.exit(1);
			} else {
				win = 0;
			}
		}
	}

	public static void showBoard() {
		System.out.println("This is the layout of the board. You play as X.");
		System.out.print("+---+---+---+\n");
		System.out.print("| ");
		System.out.print("0");
		System.out.print(" | ");
		System.out.print("1");
		System.out.print(" | ");
		System.out.print("2");
		System.out.println(" | ");
		System.out.println("+---+---+---+");
		System.out.print("| ");
		System.out.print("3");
		System.out.print(" | ");
		System.out.print("4");
		System.out.print(" | ");
		System.out.print("5");
		System.out.println(" | ");
		System.out.print("+---+---+---+\n");
		System.out.print("| ");
		System.out.print("6");
		System.out.print(" | ");
		System.out.print("7");
		System.out.print(" | ");
		System.out.print("8");
		System.out.println(" | ");
		System.out.print("+---+---+---+\n");
		System.out.println("Get ready for the game!");
	}
}
