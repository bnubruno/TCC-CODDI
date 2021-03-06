package coddi.com.br.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import coddi.com.br.coddi.R;
import coddi.com.br.model.Categoria;

/**
 * Created by Bruno on 03/04/2015.
 */
public class ListViewCategoriaAdapter extends BaseAdapter {

    private Context context;
    private List<Categoria> listaEntrada;
    private List<Categoria> listaSaida;
    private List<ItemListCategoria> listaUnificada;

    public ListViewCategoriaAdapter(Context context, List<Categoria> listaEntrada, List<Categoria> listaSaida) {
        this.context = context;
        unifica(listaEntrada, listaSaida);
    }

    private void unifica(List<Categoria> listaEntrada, List<Categoria> listaSaida) {
        if (listaEntrada == null) {
            listaEntrada = new ArrayList<>();
        }

        if (listaSaida == null) {
            listaSaida = new ArrayList<>();
        }
        this.listaEntrada = listaEntrada;
        this.listaSaida = listaSaida;

        this.listaUnificada = new ArrayList<>();
        this.listaUnificada.add(new ItemListCategoria("Categorias de entrada"));

        for (Categoria categoriaE : listaEntrada) {
            this.listaUnificada.add(new ItemListCategoria(categoriaE));
        }

        this.listaUnificada.add(new ItemListCategoria("Categorias de saída"));

        for (Categoria categoriaS : listaSaida) {
            this.listaUnificada.add(new ItemListCategoria(categoriaS));
        }
    }

    @Override
    public int getCount() {
        return listaUnificada.size();
    }

    @Override
    public Object getItem(int position) {
        return listaUnificada.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemListCategoria itemListCategoria = listaUnificada.get(position);

        if (itemListCategoria.getTipoItem() == TipoItem.N) {
            convertView = getViewNormal(convertView, itemListCategoria);
        } else if (itemListCategoria.getTipoItem() == TipoItem.S) {
            convertView = getViewSection(convertView, itemListCategoria);
        }

        return convertView;
    }

    private View getViewNormal(View convertView, ItemListCategoria itemListCategoria) {
        Categoria categoria = itemListCategoria.getCategoria();

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.categoria_lista_item, null);

        TextView txtNomeCategoria = (TextView) convertView.findViewById(R.id.txtNomeCategoria);
        txtNomeCategoria.setText(categoria.getNome());
        return convertView;
    }

    private View getViewSection(View convertView, ItemListCategoria itemListCategoria) {
        String descricao = itemListCategoria.getDescricao();

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.categoria_lista_section, null);

        TextView txtSection = (TextView) convertView.findViewById(R.id.txtNomeCategoriaSection);
        txtSection.setText(descricao);
        txtSection.setClickable(false);
        txtSection.setSelected(false);
        return convertView;
    }

    public class ItemListCategoria {

        private String descricao;
        private Categoria categoria;
        private TipoItem tipoItem;

        public ItemListCategoria(Categoria categoria) {
            this.descricao = null;
            this.categoria = categoria;
            this.tipoItem = TipoItem.N;
        }

        public ItemListCategoria(String descricao) {
            this.categoria = null;
            this.tipoItem = TipoItem.S;
            this.descricao = descricao;
        }

        public TipoItem getTipoItem() {
            return tipoItem;
        }

        public Categoria getCategoria() {
            return categoria;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    private enum TipoItem {
        N, S;
    }

    public void atualizaLista(List<Categoria> listaEntrada, List<Categoria> listaSaida) {
        unifica(listaEntrada, listaSaida);
        notifyDataSetChanged();
    }

    public List<ItemListCategoria> getListaUnificada() {
        return listaUnificada;
    }
}
