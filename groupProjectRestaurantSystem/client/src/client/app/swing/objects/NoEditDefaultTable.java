/**
 * 
 */
package client.app.swing.objects;

import javax.swing.table.DefaultTableModel;

/**
 * @author Johannes Herforth.
 *
 */
public class NoEditDefaultTable extends DefaultTableModel{


  private static final long serialVersionUID = 1L;

  public NoEditDefaultTable(Object[][] data, String[] columnNames) {
    super(data, columnNames);
  }
  
  @Override //The idea of this override was from DMunz at http://stackoverflow.com/questions/24726896/how-to-make-cells-of-jtable-non-editable-but-selectable
  public boolean isCellEditable(int row, int column) {
    return false;
  }
}
