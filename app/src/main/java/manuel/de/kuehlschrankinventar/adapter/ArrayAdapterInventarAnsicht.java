package manuel.de.kuehlschrankinventar.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.holder.Produkt;

public class ArrayAdapterInventarAnsicht extends ArrayAdapter<String> implements Filterable {
    private Activity activity;
    private ArrayList<Produkt> originalProdukte, gefilterteProdukte;

    public ArrayAdapterInventarAnsicht(@NonNull Activity activity, ArrayList<Produkt> produkte) {
        super(activity, R.layout.listview_row_produkt);
        this.activity = activity;
        this.originalProdukte = produkte;
        this.gefilterteProdukte = produkte;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.listview_row_produkt, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Produkt tempProdukt = gefilterteProdukte.get(position);
        holder.getArrow().setBackground(ResourcesCompat.getDrawable(activity.getResources(),
                android.R.drawable.arrow_down_float, null));
        holder.getProduktName().setText(tempProdukt.getName());
        //TODO allle Texte setzen
        //Verfallsdatum
        holder.getVerfallsdatum().setText("TestVerfallsdatum");
        holder.getVerfallsdatum().setVisibility(View.VISIBLE);
        //Barcode
        holder.getBarcode().setText("TestBarcode");
        holder.getBarcode().setVisibility(View.VISIBLE);
        //preisdurchschnitt
        holder.getPreisDurchschnitt().setText("Test Durchschnittspreis");
        holder.getPreisDurchschnitt().setVisibility(View.VISIBLE);
        //preisMin
        holder.getPreisMin().setText("Test Mindest Preis");
        holder.getPreisMin().setVisibility(View.VISIBLE);
        //preisMax
        holder.getPreisMax().setText("Test Maximal Preis");
        holder.getPreisMax().setVisibility(View.VISIBLE);
        holder.getPreis().setVisibility(View.GONE);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Ein- und Ausklappen des Produktes
                if (holder.istErweitert()) {
                    //einklappen
                    holder.getArrow().setBackground(ResourcesCompat.getDrawable(activity.getResources(),
                            android.R.drawable.arrow_down_float, null));
                    holder.getPreis().setVisibility(View.GONE);
                } else {
                    //ausklappen
                    holder.getArrow().setBackground(ResourcesCompat.getDrawable(activity.getResources(),
                            android.R.drawable.arrow_up_float, null));
                    holder.getPreis().setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        private final View row;
        private View preis = null;
        private TextView arrow = null, produktName = null, verfallsdatum = null, barcode = null
                , preisDurchschnitt = null, preisMax = null, preisMin = null;
        private boolean istErweitert = false;

        public ViewHolder(View row) {
            this.row = row;
        }

        public TextView getArrow() {
            if (arrow == null) {
                arrow = row.findViewById(R.id.arrow);
            }
            return arrow;
        }

        public TextView getProduktName() {
            if (produktName == null) {
                produktName = row.findViewById(R.id.produktName);
            }
            return produktName;
        }

        public TextView getVerfallsdatum() {
            if (verfallsdatum == null) {
                verfallsdatum = row.findViewById(R.id.verfallsdatum);
            }
            return verfallsdatum;
        }

        public TextView getBarcode() {
            if (barcode == null) {
                barcode = row.findViewById(R.id.barcode);
            }
            return barcode;
        }

        public TextView getPreisDurchschnitt() {
            if (preisDurchschnitt == null) {
                preisDurchschnitt = row.findViewById(R.id.preis_durchschnitt);
            }
            return preisDurchschnitt;
        }

        public TextView getPreisMax() {
            if (preisMax == null) {
                preisMax = row.findViewById(R.id.preis_max);
            }
            return preisMax;
        }

        public TextView getPreisMin() {
            if (preisMin == null) {
                preisMin = row.findViewById(R.id.preis_min);
            }
            return preisMin;
        }

        public View getPreis() {
            if (preis == null) {
                preis = row.findViewById(R.id.preis_layout);
            }
            return preis;
        }

        public boolean istErweitert() {
            return istErweitert;
        }
    }

    @Override
    public int getCount() {
        return gefilterteProdukte.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return gefilterteProdukte.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.count = originalProdukte.size();
                    results.values = originalProdukte;
                } else {
                    ArrayList<Produkt> filterResultData = new ArrayList<>();
                    for (Produkt produkt : originalProdukte) {
                        //nach Name und Barcode filtern
                        if (produkt.getName().toLowerCase().contains(constraint.toString().toLowerCase())
                                || produkt.getBarcode().contains(constraint.toString())) {
                            filterResultData.add(produkt);
                        }
                    }

                    results.count = filterResultData.size();
                    results.values = filterResultData;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                gefilterteProdukte = (ArrayList<Produkt>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}