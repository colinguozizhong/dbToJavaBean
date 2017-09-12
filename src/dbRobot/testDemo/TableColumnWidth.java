/**
 * 
 */
package dbRobot.testDemo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * @author HenryHuang
 *
 */
public class TableColumnWidth {

	private JTable table;  
	  
    public TableColumnWidth()  
    {  
        JFrame frame = new JFrame();  
        frame.getContentPane().setLayout(new BorderLayout());  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setLocale(Locale.getDefault());  
        frame.setResizable(true);  
        frame.setState(Frame.NORMAL);  
          
  
        table = new JTable();  
        String TrunkColumnNames[] = {  
            "繁华",  
            "似锦"  
        };  
        DefaultTableModel tableModel = new DefaultTableModel();  
        tableModel.setColumnIdentifiers(TrunkColumnNames);  
  
        Integer[] m = new Integer[2];  
        int j = 0;  
        for(int k=0;k<10;k++)  
        {  
            for(int i = 0;i < 2;i++)  
            {  
                j += i;  
                m[i] = j;  
            }  
            tableModel.addRow(m);  
        }  
          
  
        table.setModel(tableModel);  
        setTableHeaderWidth();  
  
        JPanel panel = new JPanel();  
        panel.setLayout(new BorderLayout());  
        JScrollPane scrollPane = new JScrollPane();  
        scrollPane.getViewport().add(table);  
        panel.add(scrollPane,BorderLayout.CENTER);  
  
        frame.getContentPane().add(panel,BorderLayout.CENTER);  
        frame.pack();  
        frame.setVisible(true);  
          
    }  
  
    /**调整表格列的宽度。*/          //1  
    private void setTableHeaderWidth()  
    {  
      for(int i=0;i<table.getColumnCount();i++)  
      {  
        TableColumn tc=table.getColumn(table.getColumnName(i));  
        int width=getPreferredWidthForColumn(tc)+10;  
        tc.setMinWidth(width);  
      }  
    }  
  
    /**获取一个列的推荐宽度，返回列名和列取值的最大宽度。*/  //2  
    private int getPreferredWidthForColumn(TableColumn col)  
    {  
      int hw=columnHeaderWidth(col),  
          cw=widestCellInColumn(col);  
      return hw>cw?hw:cw;  
    }  
  
    /**表头列的宽度*/  //3  
    private int columnHeaderWidth(TableColumn col)  
    {  
      TableCellRenderer renderer=col.getHeaderRenderer();  
      if(renderer==null)  
      {  
        renderer=table.getTableHeader().getDefaultRenderer();  
      }  
      if(renderer==null)  
          return 0;  
      Component comp=renderer.getTableCellRendererComponent(table,col.getHeaderValue(),false,false,0,0);  
      return comp.getPreferredSize().width;  
    }  
  
    /**值列的最大宽度*/  //4  
    @SuppressWarnings("unused")
	private int widestCellInColumn(TableColumn col)  
    {  
      if(true) return 0;  
      int c=col.getModelIndex(),width=0,maxw=0;  
      for(int r=0;r<table.getRowCount();++r)  
      {  
        TableCellRenderer renderer=table.getCellRenderer(r,c);  
        Component comp=renderer.getTableCellRendererComponent(table,table.getValueAt(r,c),false,false,r,c);  
        width=comp.getPreferredSize().width;  
        maxw=width>maxw?width:maxw;  
      }  
      return maxw;  
    }  
    @SuppressWarnings("unused")
	public static void main(String[] args)  
    {  
        TableColumnWidth a = new TableColumnWidth();  
    }

}
