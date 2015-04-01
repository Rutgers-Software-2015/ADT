package Shared.ADT;

public class TableHandler {
	public int[] table_array= 
	{0,0,0,0,0,0};

	public void toggle (int table){
		int t = table - 1;
		if(table_array[t] == 0){
			table_array[t] = 1;
			return;
		}
		if(table_array[t] == 1){
			table_array[t] = 0;
			return;
		}
	}
	
	public void set (int table){
		table_array[table -1] = 1;
	}

	public void unset (int table){
		table_array[table -1] = 0;
	}

	public int check (int table){
		return table_array[table -1];
	}
}


