package co.id.ez.ezpay.util.swings.table;

/**
 *
 * @author RAVEN
 */
public interface TableActionEvent {

    public boolean canView();
    public boolean canCheck();
    public boolean canReprint();
    public boolean canEdit();
    
    default boolean canDelete(){
        return false;
    }
    
    public void onView(int row);
    public void onCheckStatus(int row);
    public void onReprint(int row);
    public void onEdit(int row);
    
    default void onDelete(int row){
        
    }
}
