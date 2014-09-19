import java.util.Scanner;

public class Program {
	static int[] table = new int[9];
	static int[][] possibleWins = new int[][] { { 0, 2, 1 }, { 4, 3, 5 },
			{ 6, 8, 7 }, // ROWS
			{ 0, 6, 3 }, { 4, 1, 7 }, { 2, 8, 5 }, // COLUMNS
			{ 4, 0, 8 }, { 4, 2, 6 } }; // DIAGONALS
	private static final int SQUARES_IN_ROW = 3;
	private static final int PLAYER = 1;
	private static final int EMPTY = 0;
	private static final int CPU = -1;
	private static final int WIN = 3;

	public static void main(String args[]) {
		showBoard();
		playGame();
	}

	private static void playGame() {
		Scanner scanner = new Scanner(System.in);
		while (boardHasEmptySquare()) {
			userTurn(scanner);
			showCurrentBoard();
			checkWin(table);
			if (boardHasEmptySquare()) {
				cpuTurn();
				showCurrentBoard();
				checkWin(table);
			}
		}
		scanner.close();
	}

	private static void showCurrentBoard() {
		for (int i = 0; i < table.length; i += SQUARES_IN_ROW) {
			System.out.print("+---+---+---+\n");
			System.out.print("| ");
			printSquare(table[i]);
			System.out.print(" | ");
			printSquare(table[i + 1]);
			System.out.print(" | ");
			printSquare(table[i + 2]);
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
		int cpuMove = makeMove(table);
		table[cpuMove] = CPU;
	}

	private static void userTurn(Scanner scanner) {
		System.out.println("Make your move.");
		int userMove = readInput(scanner);
		table[userMove] = PLAYER;
	}

	private static boolean boardHasEmptySquare() {
		for (int i : table) {
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
				} else if (table[userMove] != 0) {
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
				if (table[possibleWins[i][j]] == PLAYER) {
					currentResult -= 3;
				} else if (table[possibleWins[i][j]] == CPU) {
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
			if (table[i] == EMPTY) {
				return i;
			}
		}
		return -1;
	}

	public static void checkWin(int[] board) {
		int win = 0;
		for (int i = 0; i < possibleWins.length; i++) {
			for (int j = 0; j < possibleWins[i].length; j++) {
				if (table[possibleWins[i][j]] == CPU) {
					win++;
				} else if (table[possibleWins[i][j]] == PLAYER) {
					win--;
				}
			}
			if (win == -WIN) {
				System.out.println("Sinu võit");
				System.exit(1);
			} else if (win == WIN) {
				System.out.println("Arvuti võit");
				System.exit(1);
			} else {
				win = 0;
			}
		}
	}

	public static void showBoard() {
		System.out.println("This is the layout of the board. You play as X.");
		System.out.println("+---+---+---+");
		System.out.println("| 0 | 1 | 2 |");
		System.out.println("+---+---+---+");
		System.out.println("| 3 | 4 | 5 |");
		System.out.println("+---+---+---+");
		System.out.println("| 6 | 7 | 8 |");
		System.out.println("+---+---+---+");
		System.out.println("Get ready for the game!");
	}
}
