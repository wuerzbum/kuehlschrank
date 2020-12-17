package manuel.de.kuehlschrankinventar.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.holder.Produkt;

public class ArrayAdapterInventarAnsicht extends ArrayAdapter<String> implements Filterable {
    private Activity activity;
    private final ArrayList<Produkt> originalProdukte;
    private ArrayList<Produkt> gefilterteProdukte;
    private SimpleDateFormat sDF = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

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

        //Produktname
        holder.getProduktName().setText(tempProdukt.getName());

        //Menge und Einheit
        holder.getMenge().setText(String.valueOf(tempProdukt.getMenge()));
        holder.getEinheit().setText(tempProdukt.getEinheit());

        //Verfallsdatum
        if (tempProdukt.getVerfallsdatum() != null) {
            holder.getVerfallsdatum().setText(sDF.format(tempProdukt.getVerfallsdatum()));
        } else {
            holder.getVerfallsdatum().setText("-");
        }

        //Barcode
        if (!tempProdukt.getBarcode().equals("")) {
            holder.getBarcode().setText(tempProdukt.getBarcode());
        } else {
            holder.getBarcode().setText("-");
        }

        //preisdurchschnitt
        holder.getPreisDurchschnitt().setText(String.valueOf(tempProdukt.getPreis()));

        holder.klappen(activity);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.onClick(activity);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        private final View row;
        private TextView arrow = null, produktName = null, verfallsdatum = null, barcode = null
                , preisDurchschnitt = null, menge = null, einheit = null;
        private TableRow verfallsdatumRow, preisRow, barcodeRow;
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

        public TextView getMenge() {
            if (menge == null) {
                menge = row.findViewById(R.id.menge);
            }

            return menge;
        }

        public TextView getEinheit() {
            if (einheit == null) {
                einheit = row.findViewById(R.id.einheit);
            }

            return einheit;
        }

        public TextView getVerfallsdatum() {
            if (verfallsdatum == null) {
                verfallsdatum = row.findViewById(R.id.verfallsdatum);
            }
            return verfallsdatum;
        }

        public TableRow getVerfallsdatumRow() {
            if (verfallsdatumRow == null) {
                verfallsdatumRow = row.findViewById(R.id.verfallsdatum_row);
            }

            return verfallsdatumRow;
        }

        public TextView getBarcode() {
            if (barcode == null) {
                barcode = row.findViewById(R.id.barcode);
            }
            return barcode;
        }

        public TableRow getBarcodeRow() {
            if (barcodeRow == null) {
                barcodeRow = row.findViewById(R.id.barcode_row);
            }

            return barcodeRow;
        }

        public TextView getPreisDurchschnitt() {
            if (preisDurchschnitt == null) {
                preisDurchschnitt = row.findViewById(R.id.preis_durchschnitt);
            }
            return preisDurchschnitt;
        }

        public TableRow getPreisRow() {
            if (preisRow == null) {
                preisRow = row.findViewById(R.id.preis_row);
            }

            return preisRow;
        }

        public void onClick(Activity activity) {
            if (istErweitert) {
                //einklappen
                einklappen(activity);
            } else {
                //ausklappen
                ausklappen(activity);
            }
        }

        public void klappen(Activity activity) {
            if (istErweitert) {
                ausklappen(activity);
            } else {
                einklappen(activity);
            }
        }

        private void einklappen(Activity activity) {
            getArrow().setBackground(ResourcesCompat.getDrawable(activity.getResources(),
                    android.R.drawable.arrow_down_float, null));
            getVerfallsdatumRow().setVisibility(View.GONE);
            getPreisRow().setVisibility(View.GONE);
            getBarcodeRow().setVisibility(View.GONE);
            istErweitert = false;
        }

        private void ausklappen(Activity activity) {
            getArrow().setBackground(ResourcesCompat.getDrawable(activity.getResources(),
                    android.R.drawable.arrow_up_float, null));
            getPreisRow().setVisibility(View.VISIBLE);
            getVerfallsdatumRow().setVisibility(View.VISIBLE);
            getBarcodeRow().setVisibility(View.VISIBLE);
            istErweitert = true;
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