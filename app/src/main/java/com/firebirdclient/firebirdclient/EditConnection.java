package com.firebirdclient.firebirdclient;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class EditConnection extends AppCompatActivity
{
    public int editingConnectionID = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_edit_connection);
    }

    public void onEditEndBtnClick(View view)
    {

        Connection connection=null;
        String adress = ((EditText)findViewById(R.id.AdressEditText)).getText().toString();
        String login = ((EditText)findViewById(R.id.LoginEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.PasswordEditText)).getText().toString();
        //Toast.makeText(this,"Yeah!",Toast.LENGTH_SHORT).show();
        try{
            Properties ParamConnection = new Properties();
            ParamConnection.setProperty("user", "SYSDBA");
            ParamConnection.setProperty("password", "masterkey");
     		ParamConnection.setProperty("encoding", "WIN1251");

            Class.forName("org.firebirdsql.jdbc.FBDriver");
            //Toast.makeText(getApplicationContext(), "DRIVER JDBC FIREBIRD LOADED",Toast.LENGTH_LONG).show();
            Log.println(1, "DRIVER JDBC FIREBIRD LOADED", "LOG");
            connection = DriverManager.getConnection("jdbc:firebirdsql://"+adress, ParamConnection);
        }
        catch (ClassNotFoundException e) {
            Toast.makeText(getApplicationContext(), "ERRO DE CLASSE DE DRIVER INEXISTENTE",Toast.LENGTH_LONG).show();
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "SQL Error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        if(connection!=null)
        {
            Statement stmt = null;
            try {
                stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ResultSet res = stmt.executeQuery("select 42 from RDB$DATABASE;");

                res.next();
                if(res.getInt(1)==42)
                {
                    Toast.makeText(getApplicationContext(), "Database is OK", Toast.LENGTH_LONG).show();
                    System.out.println("Database is OK");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Database is not OK",Toast.LENGTH_LONG).show();
                    System.out.println("Database is not OK");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        finish();
    }

}
