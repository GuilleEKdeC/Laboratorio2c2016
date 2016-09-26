package dam.isi.frsf.utn.edu.ar.laboratorio2c2016;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.List;
import java.util.Scanner;

import android.widget.CheckBox;
import android.app.Activity;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables ------------------------------------//

    // Líneas de la Cátedra-----------------------
    DecimalFormat f = new DecimalFormat("##.00");

    ArrayList<ElementoMenu> listaElementos;
    ElementoMenu elementoActual;
    ElementoMenu[] listaBebidas;
    ElementoMenu[] listaPlatos;
    ElementoMenu[] listaPostre;

    private TextView resumen;
    private Button confirma_pedido, agrega, reinicia;
    private Integer bandera_Confirmacion = 0;
    private String seleccion;
    private Double total;
    private TextView texto;
    private String pedido;
    RadioGroup radGrup;
    RadioButton rbpostre;
    private Button botonAgregar;
    private Button botonConfirmar;
    private Button botonReiniciar;

    ListView listView;
    ArrayAdapter<ElementoMenu> adapter;
    // -------------------------------------------


    /*------------------------------------class ElementoMenu--------------------------------------*/
    /* Esta clase contendrá la información de cada item de la lista ------------------------------*/
    class ElementoMenu {

        private Integer id;
        private String nombre;
        private Double precio;

        //                  CONSTRUCTORES
        //---------------------------------------------------
        public ElementoMenu() {
        }

        //---------------------------------------------------
        public ElementoMenu(Integer i, String n, Double p) {
            this.setId(i);
            this.setNombre(n);
            this.setPrecio(p);
        }

        //---------------------------------------------------
        public ElementoMenu(Integer i, String n) {
            this(i,n,0.0);
            Random r = new Random();
            this.precio= (r.nextInt(3)+1)*((r.nextDouble()*100));
        }

        //                  GETs Y SETs
        //---------------------------------------------------
        public Integer getId() {
            return id;
        }
        //---------------------------------------------------
        public void setId(Integer id) {
            this.id = id;
        }
        //---------------------------------------------------
        public String getNombre() {
            return nombre;
        }
        //---------------------------------------------------
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        //---------------------------------------------------
        public Double getPrecio() {
            return precio;
        }
        //---------------------------------------------------
        public void setPrecio(Double precio) {
            this.precio = precio;
        }

        @Override
        //                  TO STRING
        //---------------------------------------------------
        public String toString() {
            return this.nombre+ "( "+f.format(this.precio)+" )";
        }
    }
    /*-----------------------------------Inicializar Listas---------------------------------------*/
    private void iniciarListas(){
        // inicia lista de bebidas
        listaBebidas = new ElementoMenu[7];
        listaBebidas[0]=new ElementoMenu(1,"Coca");
        listaBebidas[1]=new ElementoMenu(4,"Jugo");
        listaBebidas[2]=new ElementoMenu(6,"Agua");
        listaBebidas[3]=new ElementoMenu(8,"Soda");
        listaBebidas[4]=new ElementoMenu(9,"Fernet");
        listaBebidas[5]=new ElementoMenu(10,"Vino");
        listaBebidas[6]=new ElementoMenu(11,"Cerveza");
        // inicia lista de platos
        listaPlatos= new ElementoMenu[14];
        listaPlatos[0]=new ElementoMenu(1,"Ravioles");
        listaPlatos[1]=new ElementoMenu(2,"Gnocchi");
        listaPlatos[2]=new ElementoMenu(3,"Tallarines");
        listaPlatos[3]=new ElementoMenu(4,"Lomo");
        listaPlatos[4]=new ElementoMenu(5,"Entrecot");
        listaPlatos[5]=new ElementoMenu(6,"Pollo");
        listaPlatos[6]=new ElementoMenu(7,"Pechuga");
        listaPlatos[7]=new ElementoMenu(8,"Pizza");
        listaPlatos[8]=new ElementoMenu(9,"Empanadas");
        listaPlatos[9]=new ElementoMenu(10,"Milanesas");
        listaPlatos[10]=new ElementoMenu(11,"Picada 1");
        listaPlatos[11]=new ElementoMenu(12,"Picada 2");
        listaPlatos[12]=new ElementoMenu(13,"Hamburguesa");
        listaPlatos[12]=new ElementoMenu(14,"Calamares");
        // inicia lista de postres
        listaPostre= new ElementoMenu[15];
        listaPostre[0]=new ElementoMenu(1,"Helado");
        listaPostre[1]=new ElementoMenu(2,"Ensalada de Frutas");
        listaPostre[2]=new ElementoMenu(3,"Macedonia");
        listaPostre[3]=new ElementoMenu(4,"Brownie");
        listaPostre[4]=new ElementoMenu(5,"Cheescake");
        listaPostre[5]=new ElementoMenu(6,"Tiramisu");
        listaPostre[6]=new ElementoMenu(7,"Mousse");
        listaPostre[7]=new ElementoMenu(8,"Fondue");
        listaPostre[8]=new ElementoMenu(9,"Profiterol");
        listaPostre[9]=new ElementoMenu(10,"Selva Negra");
        listaPostre[10]=new ElementoMenu(11,"Lemon Pie");
        listaPostre[11]=new ElementoMenu(12,"KitKat");
        listaPostre[12]=new ElementoMenu(13,"IceCreamSandwich");
        listaPostre[13]=new ElementoMenu(14,"Frozen Yougurth");
        listaPostre[14]=new ElementoMenu(15,"Queso y Batata");

    }
    /*-----------------------------------------On Create------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setea el Spinner con los horarios posibles, cargados en el recurso arrays.xml
        String[] horas = getResources().getStringArray(R.array.times);
        ArrayAdapter adaptadorHorario = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, horas);
        Spinner spinnerHorario;// = new Spinner();
        spinnerHorario = (Spinner)findViewById(R.id.spinner);
        spinnerHorario.setAdapter(adaptadorHorario);
        //--------------Fin del Seteo Spinner-----------------------//


        // Setea el TextView que muestra el Pedido que va armando el cliente
        texto = (TextView) findViewById(R.id.textView3);
        pedido ="";
        texto.setMovementMethod(new ScrollingMovementMethod());
        //--------------Fin del Seteo TextView---------------------//


        //Instanciación del ListView y sus Adaptadores
        total = 0.0;
        seleccion = null;
        elementoActual = null;
        iniciarListas();
        listaElementos = new ArrayList<ElementoMenu>();
        listaElementos.addAll(Arrays.asList(listaPostre));

        listView = (ListView) findViewById(R.id.MyList);
        adapter = new ArrayAdapter<ElementoMenu>(this, android.R.layout.simple_list_item_single_choice,listaElementos);
        listView.setAdapter(adapter);
        //-------------------Fin de la Instanciacion LV------------//


        //Manejo del Radio Group Horizontal de Selección de menú (plato, postre o bebida)
        radGrup = (RadioGroup) findViewById(R.id.radioGroup);

        radGrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                listView.clearChoices();
                listView.setItemChecked(-1, true);

                listaElementos.clear();
                switch (checkedId) {
                    case R.id.radioButton:
                        //el adaptador muestra en pantalla los platos
                        listaElementos.addAll(Arrays.asList(listaPlatos));
                        break;
                    case R.id.radioButton2:
                        //el adaptador muestra en pantalla los postres
                        listaElementos.addAll(Arrays.asList(listaPostre));
                        break;
                    case R.id.radioButton3:
                        //el adaptador muestra en pantalla la bebida
                        listaElementos.addAll(Arrays.asList(listaBebidas));
                        break;
                }

                adapter.notifyDataSetChanged();
            }

        });
        //--------------------Fin del Radio Group-------------------//


        //Manejo de la Selección de un Item (comida)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Guardamos en una variable el item comida seleccionado
                elementoActual = (ElementoMenu) listView.getItemAtPosition(position);
            }
        });
        //---------------------Fin Selección Item Comida-------------//


        botonAgregar = (Button)findViewById(R.id.button);
        botonConfirmar = (Button)findViewById(R.id.button2);
        botonReiniciar = (Button)findViewById(R.id.button3);

        botonAgregar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0){
                int indx = 0;
                if(bandera_Confirmacion==1){ //Si quiere agregar y ya habia confirmado => MSJ ERROR
                    Toast.makeText(getApplicationContext(), R.string.warning2, Toast.LENGTH_SHORT).show();
                }
                else{//si quiere agregar y no habia confirmado => AGREGA
                    if(elementoActual == null){
                        Toast.makeText(getApplicationContext(), R.string.warning1, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        pedido += elementoActual.toString() + "\n";
                        //texto.setText(texto.getText() + elementoActual.toString() +"\n");
                        total += elementoActual.getPrecio();
                        texto.setText(pedido + "TOTAL: " + String.format("%.2f", total) + "\n");}
                }
            }
        });

        botonConfirmar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0)
            {
                //una vez que confirma guardamos la acción y la Lista del Pedido
                if(pedido.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.warning1, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.warning3, Toast.LENGTH_SHORT).show();
                    bandera_Confirmacion = 1;
                }

            }
        });

        botonReiniciar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                //se desconfirma la acción y se resetea la Lista de Pedido
                bandera_Confirmacion = 0;
                pedido = "";
                total = 0.0;
                texto.setText("");
                elementoActual = null;
                listView.clearChoices();
                listaElementos.clear();
                radGrup.check(R.id.radioButton2);
                listaElementos.addAll(Arrays.asList(listaPostre));
                adapter.notifyDataSetChanged();
            }
        });

    }//Fin OnCreate


}
