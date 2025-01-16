package org.tool.vistor;

import net.sf.jsqlparser.statement.select.*;

import java.nio.file.Paths;
import java.util.List;

public class MySelectVisitor implements SelectVisitor {
    @Override
    public Object visit(ParenthesedSelect parenthesedSelect, Object context) {
        System.out.println("parenthesedSelect = " + parenthesedSelect + ", context = " + context);
        PlainSelect plainSelect = parenthesedSelect.getPlainSelect();
        plainSelect.accept(this, context);
        return null;
    }

    @Override
    public Object visit(PlainSelect plainSelect, Object context) {
        System.out.println("plainSelect = " + plainSelect + ", context = " + context);
        List<WithItem> withItemList = plainSelect.getWithItemsList();
        List<SelectItem<?>> selectItemList = plainSelect.getSelectItems();

        if (withItemList != null) {
            for (WithItem withItem : withItemList) {
                withItem.accept(this, context);
            }
        }

        if (selectItemList != null) {
            for (SelectItem selectItem : selectItemList) {
                selectItem.accept(new MySelectItemVisitor(), context);
            }
        }
        return null;
    }

    @Override
    public Object visit(SetOperationList setOpList, Object context) {
        System.out.println("setOpList = " + setOpList + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(WithItem withItem, Object context) {
        System.out.println("withItem = " + withItem + ", context = " + context);
        Select select = withItem.getSelect();

        select.accept(this, context);
        return null;
    }

    @Override
    public Object visit(Values values, Object context) {
        System.out.println("This is Values");
        return null;
    }

    @Override
    public Object visit(LateralSubSelect lateralSubSelect, Object context) {
        System.out.println("This is LateralSubSelect");
        return null;
    }

    @Override
    public Object visit(TableStatement tableStatement, Object context) {
        System.out.println("This is TableStatement");
        return null;
    }
}
