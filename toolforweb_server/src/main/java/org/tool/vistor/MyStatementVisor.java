package org.tool.vistor;

import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterSession;
import net.sf.jsqlparser.statement.alter.AlterSystemStatement;
import net.sf.jsqlparser.statement.alter.RenameTableStatement;
import net.sf.jsqlparser.statement.alter.sequence.AlterSequence;
import net.sf.jsqlparser.statement.analyze.Analyze;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.schema.CreateSchema;
import net.sf.jsqlparser.statement.create.sequence.CreateSequence;
import net.sf.jsqlparser.statement.create.synonym.CreateSynonym;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.grant.Grant;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.refresh.RefreshMaterializedViewStatement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.show.ShowIndexStatement;
import net.sf.jsqlparser.statement.show.ShowTablesStatement;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;

import java.util.List;

public class MyStatementVisor implements StatementVisitor {

    @Override
    public Object visit(Analyze analyze, Object context) {
        System.out.println("analyze = " + analyze + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(SavepointStatement savepointStatement, Object context) {
        System.out.println("savepointStatement = " + savepointStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(RollbackStatement rollbackStatement, Object context) {
        System.out.println("rollbackStatement = " + rollbackStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Comment comment, Object context) {
        System.out.println("comment = " + comment + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Commit commit, Object context) {
        System.out.println("commit = " + commit + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Delete delete, Object context) {
        System.out.println("delete = " + delete + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Update update, Object context) {
        System.out.println("update = " + update + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Insert insert, Object context) {
        System.out.println("insert = " + insert + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Drop drop, Object context) {
        System.out.println("drop = " + drop + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Truncate truncate, Object context) {
        System.out.println("truncate = " + truncate + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(CreateIndex createIndex, Object context) {
        System.out.println("createIndex = " + createIndex + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(CreateSchema createSchema, Object context) {
        System.out.println("createSchema = " + createSchema + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(CreateTable createTable, Object context) {
        System.out.println("createTable = " + createTable + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(CreateView createView, Object context) {
        System.out.println("createView = " + createView + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(AlterView alterView, Object context) {
        System.out.println("alterView = " + alterView + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(RefreshMaterializedViewStatement materializedView, Object context) {
        System.out.println("materializedView = " + materializedView + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Alter alter, Object context) {
        System.out.println("alter = " + alter + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Statements statements, Object context) {
        System.out.println("statements = " + statements + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Execute execute, Object context) {
        System.out.println("execute = " + execute + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(SetStatement set, Object context) {
        System.out.println("set = " + set + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(ResetStatement reset, Object context) {
        System.out.println("reset = " + reset + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(ShowColumnsStatement showColumns, Object context) {
        System.out.println("showColumns = " + showColumns + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(ShowIndexStatement showIndex, Object context) {
        System.out.println("showIndex = " + showIndex + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(ShowTablesStatement showTables, Object context) {
        System.out.println("showTables = " + showTables + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Merge merge, Object context) {
        System.out.println("merge = " + merge + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Select select, Object context) {
        System.out.println("select = " + select + ", context = " + context);
        select.accept(new MySelectVisitor(), context);
        return null;
    }

    @Override
    public Object visit(Upsert upsert, Object context) {
        System.out.println("upsert = " + upsert + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(UseStatement use, Object context) {
        System.out.println("use = " + use + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Block block, Object context) {
        System.out.println("block = " + block + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(DescribeStatement describe, Object context) {
        System.out.println("describe = " + describe + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(ExplainStatement explainStatement, Object context) {
        System.out.println("explainStatement = " + explainStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(ShowStatement showStatement, Object context) {
        System.out.println("showStatement = " + showStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(DeclareStatement declareStatement, Object context) {
        System.out.println("declareStatement = " + declareStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(Grant grant, Object context) {
        System.out.println("grant = " + grant + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(CreateSequence createSequence, Object context) {
        System.out.println("createSequence = " + createSequence + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(AlterSequence alterSequence, Object context) {
        System.out.println("alterSequence = " + alterSequence + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(CreateFunctionalStatement createFunctionalStatement, Object context) {
        System.out.println("createFunctionalStatement = " + createFunctionalStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(CreateSynonym createSynonym, Object context) {
        System.out.println("createSynonym = " + createSynonym + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(AlterSession alterSession, Object context) {
        System.out.println("alterSession = " + alterSession + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(IfElseStatement ifElseStatement, Object context) {
        System.out.println("ifElseStatement = " + ifElseStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(RenameTableStatement renameTableStatement, Object context) {
        System.out.println("renameTableStatement = " + renameTableStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(PurgeStatement purgeStatement, Object context) {
        System.out.println("purgeStatement = " + purgeStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(AlterSystemStatement alterSystemStatement, Object context) {
        System.out.println("alterSystemStatement = " + alterSystemStatement + ", context = " + context);
        return null;
    }

    @Override
    public Object visit(UnsupportedStatement unsupportedStatement, Object context) {
        System.out.println("unsupportedStatement = " + unsupportedStatement + ", context = " + context);
        return null;
    }
}
