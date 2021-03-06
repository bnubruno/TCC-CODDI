package coddi.com.br.App;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import coddi.com.br.dao.CategoriaDAO;
import coddi.com.br.model.Categoria;
import coddi.com.br.model.Conta;
import coddi.com.br.model.Fila;
import coddi.com.br.model.Lancamento;
import coddi.com.br.model.TipoFinanceiro;
import coddi.com.br.model.Usuario;

/**
 * Created by Bruno on 05/02/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "coddi.sqlite";
    private static final int DATABASE_VERSION = 62;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Categoria.class);
            TableUtils.createTable(connectionSource, Conta.class);
            TableUtils.createTable(connectionSource, Lancamento.class);
            TableUtils.createTable(connectionSource, Fila.class);

//            CategoriaDAO categoriaDAO = new CategoriaDAO(connectionSource);
//            Categoria categoria = new Categoria();
//            categoria.setNome("Salário");
//            categoria.setTipoFinanceiro(TipoFinanceiro.ENTRADA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Bônus");
//            categoria.setTipoFinanceiro(TipoFinanceiro.ENTRADA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Subsídio");
//            categoria.setTipoFinanceiro(TipoFinanceiro.ENTRADA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Outros");
//            categoria.setTipoFinanceiro(TipoFinanceiro.ENTRADA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Alimentação");
//            categoria.setTipoFinanceiro(TipoFinanceiro.SAIDA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Eventos");
//            categoria.setTipoFinanceiro(TipoFinanceiro.SAIDA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Transporte");
//            categoria.setTipoFinanceiro(TipoFinanceiro.SAIDA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Lazer");
//            categoria.setTipoFinanceiro(TipoFinanceiro.SAIDA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Vestuário");
//            categoria.setTipoFinanceiro(TipoFinanceiro.SAIDA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Saúde");
//            categoria.setTipoFinanceiro(TipoFinanceiro.SAIDA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Educação");
//            categoria.setTipoFinanceiro(TipoFinanceiro.SAIDA);
//            categoriaDAO.incluir(categoria);
//
//            categoria = new Categoria();
//            categoria.setNome("Movimentação");
//            categoria.setTipoFinanceiro(TipoFinanceiro.MOVIMENTACAO);
//            categoriaDAO.incluir(categoria);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Usuario.class, true);
            TableUtils.dropTable(connectionSource, Categoria.class, true);
            TableUtils.dropTable(connectionSource, Conta.class, true);
            TableUtils.dropTable(connectionSource, Lancamento.class, true);
            TableUtils.dropTable(connectionSource, Fila.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
