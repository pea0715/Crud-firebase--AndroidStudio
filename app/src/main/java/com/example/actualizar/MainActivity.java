package com.example.actualizar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText Nombre,Telefono,Estatura,ID;
    Button  insertar,Listar,Actualizar,Eliminar;
    ListView MostrarPreguntas;
    DatabaseReference miDB;
    ArrayList<String> Lista = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miDB = FirebaseDatabase.getInstance().getReference();
        conectar();
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insertar();
            }
        });
        MostrarPreguntas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String IdActual = MostrarPreguntas.getItemAtPosition(i).toString();
                String[] datos = IdActual.split(" ");
                String idn = datos[0];
                ID.setText(idn);
                String nombre = datos[1];
                Nombre.setText(nombre);
                String telefono = datos[2];
                Telefono.setText(telefono);
                String edad = datos[3];
                Estatura.setText(edad);
                }
            });
        Listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listar();
            }
        });
        Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actulizar();
            }
        });
        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar();
            }
        });
    }
    private  void  actulizar()
    {
        Contacto C= new Contacto( Nombre.getText().toString(), Telefono.getText().toString(), Integer.valueOf(Estatura.getText().toString()), ID.getText().toString());
        miDB.child("Contacto").child(ID.getText().toString()).setValue(C);
        Toast.makeText(this,"registro actualizado",Toast.LENGTH_SHORT).show();
    }
    private  void eliminar()
    {
        Contacto C= new Contacto( Nombre.getText().toString(), Telefono.getText().toString(), Integer.valueOf(Estatura.getText().toString()), ID.getText().toString());
        miDB.child("Contacto").child(ID.getText().toString()).removeValue();
        Toast.makeText(this,"registro eliminado ",Toast.LENGTH_SHORT).show();
    }
    private void Insertar()
    {
        String nId = UUID.randomUUID().toString();
        String nombre = Nombre.getText().toString();
        String TEl = Telefono.getText().toString();
        int Edad = Integer.valueOf(Estatura.getText().toString());
        Contacto C = new Contacto(nombre,TEl,Edad, nId);
        Toast.makeText(this,"exito", Toast.LENGTH_SHORT);
        miDB.child("Contacto").child(C.getId()).setValue(C);
    }
    private void Listar()
    {
        miDB.child("Contacto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lista.clear();
                for(DataSnapshot objsnapshot: snapshot.getChildren()){
                    String C = objsnapshot.child("id").getValue().toString() + " " +
                               objsnapshot.child("nombre").getValue().toString() + " " +
                               objsnapshot.child("telefono").getValue().toString() + " " +
                               objsnapshot.child("edad").getValue().toString();

                    Lista.add(C);
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Lista);
                MostrarPreguntas.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void conectar() {
        Nombre = findViewById(R.id.Nombre);
        Telefono = findViewById(R.id.Telefono);
        Estatura = findViewById(R.id.Estatura);
        ID = findViewById(R.id.ID);
        insertar = findViewById(R.id.insertar);
        Listar = findViewById(R.id.Listar);
        Actualizar = findViewById(R.id.Actualizar);
        Eliminar = findViewById(R.id.Eliminar);
        MostrarPreguntas = findViewById(R.id.MostrarPreguntas);
    }
}