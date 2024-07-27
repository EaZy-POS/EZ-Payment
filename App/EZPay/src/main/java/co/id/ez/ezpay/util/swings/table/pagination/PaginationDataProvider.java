package co.id.ez.ezpay.util.swings.table.pagination;

import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import java.util.LinkedList;
import java.util.List;

public class PaginationDataProvider<T, E extends TableHeader> {

    private LinkedList<T> dataList;
    private LinkedList<E> headerList;
    private TableActionEvent actionEvent;

    public PaginationDataProvider create(LinkedList<E> headerlist, LinkedList<T> dataList) {
        return create(headerlist, dataList, null);
    }

    public PaginationDataProvider create(LinkedList<E> headerlist, LinkedList<T> dataList, TableActionEvent event) {
        this.dataList = dataList;
        this.headerList = headerlist;
        this.actionEvent = event;
        return this;
    }

    public int getTotalRowCount() {
        if(dataList == null){
            return 0;
        }
        return dataList.size();
    }

    public LinkedList<T> getRows(int startIndex, int endIndex) {
        LinkedList<T> data = new LinkedList<>();
        if (dataList != null) {
            List<T> temData = dataList.subList(startIndex, endIndex);

            temData.forEach(t -> {
                data.add(t);
            });
        }

        return data;
    }

    public LinkedList<E> getColumn() {
        return headerList;
    }

    public TableActionEvent getActionEvent() {
        return actionEvent;
    }
}
