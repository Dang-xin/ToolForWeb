package org.tool.vistor;

import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;

public class MySelectItemVisitor implements SelectItemVisitor {
    @Override
    public Object visit(SelectItem selectItem, Object context) {
        System.out.println("selectItem = " + selectItem + ", context = " + context);
        return null;
    }

    @Override
    public void visit(SelectItem selectItem) {
        SelectItemVisitor.super.visit(selectItem);
    }
}
