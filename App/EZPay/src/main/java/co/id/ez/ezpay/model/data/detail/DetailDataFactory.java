/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.detail;

import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.enums.util.Aligment;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class DetailDataFactory {

    private final LinkedList<DetailModel> detailList = new LinkedList<>();
    private final int maxLength;

    public DetailDataFactory(int length) {
        this.maxLength = length;
    }

    public DetailDataFactory() {
        this(-1);
    }

    public DetailDataFactory create(Field[]... field) {
        for (Field[] fields : field) {
            addData(fields);
        }
        return this;
    }

    public DetailDataFactory create(Field... field) {
        addData(field);
        return this;
    }

    private void addData(Field... field) {
        String value = "";
        boolean isHasOverlapData = false;
        Field[] overlapData = new Field[field.length];

        int remaininglength = maxLength;
        
        int index = 0;
        for (Field data : field) {
            String val = data.getValue();
            String pad = data.getPadding();
            Aligment al = data.getAligment();
            int subs = data.getLength();

            String overLaps;

            if (subs > 0 && val.length() > subs) {
                overLaps = val.substring(subs);
                if (!isHasOverlapData) {
                    isHasOverlapData = true;
                }
            } else {
                overLaps = "";
            }

            if (subs > 0) {
                if (maxLength > 0) {
                    if (subs > remaininglength) {
                        subs = remaininglength;
                    }
                }
            } else {
                subs = 0;
            }

            String tem;
            if (subs > 0) {
                switch (al) {
                    case LEFT:
                        tem = Common.rigthPadding(val, subs, pad);
                        break;
                    case RIGHT:
                        tem = Common.leftPadding(val, subs, pad);
                        break;
                    default:
                        tem = Common.centerPadding(val, subs, pad);
                        break;
                }
            } else {
                tem = val;
            }

            value = value.concat(tem);
            remaininglength = remaininglength - subs;
            overlapData[index] = new Field(overLaps, data.getLength(), al, pad);
            index++;
        }

        detailList.add(new DetailModel(value));

        if (isHasOverlapData) {
            addData(overlapData);
        }
    }

    public LinkedList<DetailModel> getDetailList() {
        return detailList;
    }

    @Override
    public String toString() {
        String value = "";

        for (DetailModel detailModel : detailList) {
            value = value.concat(detailModel.getValue()).concat("\n");
        }

        return value;
    }

    public static void main(String[] args) {
        DetailDataFactory fact = new DetailDataFactory(20);

        fact.addData(
                new Field("Nama Pelanggan", 8, Aligment.LEFT, " "),
                new Field(":", 2, Aligment.LEFT, " "),
                new Field("Muhammad Lutfi riyanto", 10, Aligment.LEFT, " ")
        );

        System.out.println(fact.toString());
    }
}
