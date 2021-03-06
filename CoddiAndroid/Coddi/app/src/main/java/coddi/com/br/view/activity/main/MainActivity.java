package coddi.com.br.view.activity.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import coddi.com.br.App.CoddiApplication;
import coddi.com.br.coddi.R;
import coddi.com.br.controller.BOPool;
import coddi.com.br.view.activity.main.fragment.CategoriaFragment;
import coddi.com.br.view.activity.main.fragment.ContaFragment;
import coddi.com.br.view.activity.main.fragment.PagamentoFragment;
import coddi.com.br.view.activity.main.fragment.RecebimentoFragment;
import coddi.com.br.view.activity.main.fragment.ResultadoMensalFragment;
import coddi.com.br.view.activity.main.fragment.SaqueFragment;
import coddi.com.br.view.activity.main.fragment.TipoMenu;
import coddi.com.br.view.activity.main.fragment.TransferenciaFragment;


public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private List<NavigationDrawerItem> mDavigationDrawer;

    private TaskSincronia task = null;
    private Timer timer;
    private BOPool pool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        TypedArray arrayIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        pool = BOPool.getInstance((CoddiApplication) getApplicationContext());

        List<NavigationDrawerItem> itens = new ArrayList<>();

        itens.add(new NavigationDrawerItem("Cadastros", true));
        itens.add(new NavigationDrawerItem(TipoMenu.CONTA.getDescricao(), arrayIcons.getResourceId(TipoMenu.CONTA.getPosicaoIcone(), -1)));
        itens.add(new NavigationDrawerItem(TipoMenu.CATEGORIA.getDescricao(), arrayIcons.getResourceId(TipoMenu.CATEGORIA.getPosicaoIcone(), -1)));
        itens.add(new NavigationDrawerItem("Operações", true));
        itens.add(new NavigationDrawerItem(TipoMenu.PAGAMENTO.getDescricao(), arrayIcons.getResourceId(TipoMenu.PAGAMENTO.getPosicaoIcone(), -1)));
        itens.add(new NavigationDrawerItem(TipoMenu.SAQUE.getDescricao(), arrayIcons.getResourceId(TipoMenu.SAQUE.getPosicaoIcone(), -1)));
        itens.add(new NavigationDrawerItem(TipoMenu.RECEBIMENTO.getDescricao(), arrayIcons.getResourceId(TipoMenu.RECEBIMENTO.getPosicaoIcone(), -1)));
        itens.add(new NavigationDrawerItem(TipoMenu.TRANSFERENCIA.getDescricao(), arrayIcons.getResourceId(TipoMenu.TRANSFERENCIA.getPosicaoIcone(), -1)));
        itens.add(new NavigationDrawerItem("Consultas", true));
        itens.add(new NavigationDrawerItem(TipoMenu.RESULTADO.getDescricao(), arrayIcons.getResourceId(TipoMenu.RESULTADO.getPosicaoIcone(), -1)));

        mDavigationDrawer = itens;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new NavigationDrawerItemAdapter(getApplicationContext(), itens));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(1);
        }

        runTimer();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {
        if (position == TipoMenu.CATEGORIA.getId()) {
            ContaFragment fragment = new ContaFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mDavigationDrawer.get(position).getTitulo());
            mDrawerLayout.closeDrawer(mDrawerList);
        }

        if (position == TipoMenu.CONTA.getId()) {
            CategoriaFragment fragment = new CategoriaFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mDavigationDrawer.get(position).getTitulo());
            mDrawerLayout.closeDrawer(mDrawerList);
        }

        if (position == TipoMenu.PAGAMENTO.getId()) {
            PagamentoFragment fragment = new PagamentoFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mDavigationDrawer.get(position).getTitulo());
            mDrawerLayout.closeDrawer(mDrawerList);
        }

        if (position == TipoMenu.SAQUE.getId()) {
            SaqueFragment fragment = new SaqueFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mDavigationDrawer.get(position).getTitulo());
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        if (position == TipoMenu.RECEBIMENTO.getId()) {
            RecebimentoFragment fragment = new RecebimentoFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mDavigationDrawer.get(position).getTitulo());
            mDrawerLayout.closeDrawer(mDrawerList);
        }

        if (position == TipoMenu.TRANSFERENCIA.getId()) {
            TransferenciaFragment fragment = new TransferenciaFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mDavigationDrawer.get(position).getTitulo());
            mDrawerLayout.closeDrawer(mDrawerList);
        }

        if (position == TipoMenu.RESULTADO.getId()) {
            ResultadoMensalFragment fragment = new ResultadoMensalFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mDavigationDrawer.get(position).getTitulo());
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    public Timer runTimer() {

        if (timer == null) {
            MyTimerTask timerTask = new MyTimerTask();

            timer = new Timer();
            timer.schedule(timerTask, 1000, 5000);
        }
        return timer;
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            task = new TaskSincronia();
            task.execute();
        }
    }

    public class TaskSincronia extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                pool.getFilaBO().sincroniza();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            task = null;
        }

        @Override
        protected void onCancelled() {
            task = null;
        }
    }


}
