/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.master;

/**
 *
 * @author Lutfi
 */
public class AppSubModulModel extends AppModulModel {

    private final boolean masterData;
    private CrudAccess crudAccess;

    public AppSubModulModel(String module) {
        super(module);
        this.masterData = false;
        this.crudAccess = null;
    }

    public AppSubModulModel(String module, boolean isMasterData) {
        super(module);
        this.masterData = isMasterData;
        if (isMasterData) {
            this.crudAccess = new CrudAccess(false, false, false);
        } else {
            this.crudAccess = null;
        }
    }

    public boolean isMasterData() {
        return masterData;
    }

    public CrudAccess getCrudAccess() {
        return crudAccess;
    }

    public void setCanWrite(boolean access) {
        if (isMasterData()) {
            getCrudAccess().setCanWrite(access);
        }
    }

    public void setCanUpdate(boolean access) {
        if (isMasterData()) {
            getCrudAccess().setCanUpdate(access);
        }
    }

    public void setCanDelete(boolean access) {
        if (isMasterData()) {
            getCrudAccess().setCanDelete(access);
        }
    }

    @Override
    public Object[] getArrayData() {
        Object[] result;
        if (masterData && crudAccess != null) {
            result = new Object[]{
                isCheck(),
                getModule(),
                getCrudAccess().isCanWrite,
                getCrudAccess().isCanUpdate,
                getCrudAccess().isCanDelete
            };
        } else {
            result = new Object[]{isCheck(), getModule(), null, null, null};
        }

        return result;
    }

    @Override
    public String toString() {
        return "AppSubModulModel{"
                + "isMasterData=" + masterData
                + ", submodule=" + getModule()
                + ", isCanWrite=" + getCrudAccess().isCanWrite
                + ", isCanEdit=" + getCrudAccess().isCanUpdate
                + ", isCanDelete=" + getCrudAccess().isCanDelete
                + '}';
    }

    public void setSelected(Object select) {
        setCheck((boolean) select);
    }

    @Override
    public void setSelected(Object select, int columnIndex) {
        if (columnIndex == 0) {
            setSelected(select);
        } else {
            if (isMasterData()) {
                if (isCheck()) {
                    if (columnIndex == 2) {
                        getCrudAccess().isCanWrite = (boolean) select;
                    }

                    if (columnIndex == 3) {
                        getCrudAccess().isCanUpdate = (boolean) select;
                    }

                    if (columnIndex == 4) {
                        getCrudAccess().isCanDelete = (boolean) select;
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        setSelected(false, 0);
    }
    
    public void resetCrudAccess() {
        reset();
        if (isMasterData()) {
            this.crudAccess = new CrudAccess();
        }
    }

    @Override
    public void grantAll() {
        setSelected(true, 0);
        if (isMasterData()) {
            this.crudAccess = new CrudAccess(true);
        }
    }

    public class CrudAccess {

        private boolean isCanWrite, isCanUpdate, isCanDelete;

        public CrudAccess() {
            this(false, false, false);
        }

        public CrudAccess(boolean grantAll) {
            this(grantAll, grantAll, grantAll);
        }

        public void setCanWrite(boolean isCanWrite) {
            this.isCanWrite = isCanWrite;
        }

        public void setCanUpdate(boolean isCanUpdate) {
            this.isCanUpdate = isCanUpdate;
        }

        public void setCanDelete(boolean isCanDelete) {
            this.isCanDelete = isCanDelete;
        }

        public CrudAccess(boolean isCanWrite, boolean isCanUpdate, boolean isCanDelete) {
            this.isCanWrite = isCanWrite;
            this.isCanUpdate = isCanUpdate;
            this.isCanDelete = isCanDelete;
        }

        public boolean isCanWrite() {
            return isCanWrite;
        }

        public boolean isCanUpdate() {
            return isCanUpdate;
        }

        public boolean isCanDelete() {
            return isCanDelete;
        }

        public String getCrudAccessString(){
            String tResult = "{";
            
            String tCanWrite = isCanWrite ? "create" : "";
            String tCanUpdate = isCanUpdate ? "update" : "";
            String tCanDelete = isCanDelete ? "delete" : "";
            
            tResult = tResult.concat(tCanWrite).concat(tCanWrite.length() > 0 ? "|" : "");
            tResult = tResult.concat(tCanUpdate).concat(tCanUpdate.length() > 0 ? "|" : "");
            tResult = tResult.concat(tCanDelete);
            
            tResult = tResult.concat("}");
            
            return tResult;
        }
    }
}
