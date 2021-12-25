import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class LeaderBoard extends JFrame {
	private ArrayList<String> names;
	private ArrayList<Integer> scores;
	private ArrayList<Integer> waves;
	private JTable table;
	private JScrollPane scrollPane;
	public LeaderBoard() {
		setTitle("Leaderboard");
		names = new ArrayList<String>();
		scores = new ArrayList<Integer>();
		waves = new ArrayList<Integer>();
		
		//set header and data for leaderboard
		String[] header = {"Players","Score","Wave Count"};
		Object[][] info = getInfo();
		//creates a new jtable with the header and data
		table = new JTable(info,header);
		//puts table in a scrollpane
		scrollPane = new JScrollPane(table);
		//allows for rows to be sortable
		table.setAutoCreateRowSorter(true);
		//disables table editing features
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		//adds scrollpane to the jframe
		add(scrollPane);
		
		this.setSize(400, 400);
		this.setFocusable(true);
		this.setLocationRelativeTo(null);
		setVisible(true);
		
		
		
	}
	/**
	 * returns a 2d array of objects representing leaderboard data
	 */
	private Object[][] getInfo(){
		Object[][] leaderboardInfo;
		File file = new File("stats.txt");
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//while there is another line of input
		while(input.hasNextLine()){
			String line = input.nextLine();
			//adds name on that line to the name array
			names.add(line.substring(0,line.indexOf(',')));
			//adds score on that line to the score array
			scores.add(Integer.parseInt(line.substring(line.indexOf(',') +1, line.lastIndexOf(','))));
			//adds wave count on that line to waves array
			waves.add(Integer.parseInt(line.substring(line.lastIndexOf(',')+1)));
		}
		//creates array of objects with the appropriate amount of rows (based on number of names collected) and columns
		leaderboardInfo = new Object[names.size()][3];
		//loops through array
		for(int i = 0;i<leaderboardInfo[0].length;i++) {
			for(int j =0;j<leaderboardInfo.length;j++) {
				//enters the correct element depending on the column
				switch(i) {
				case 0:
					leaderboardInfo[j][0] = names.get(j); break;
				case 1:
					leaderboardInfo[j][1] = scores.get(j);break;
				case 2:
					leaderboardInfo[j][2] = waves.get(j);break;
				}
			}
		}
		return leaderboardInfo;
		
	}
	
	


}
