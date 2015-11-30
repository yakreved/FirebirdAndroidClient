package data;

import java.util.List;

/**
 * Created by root on 01.12.15.
 */
public class ApplicationDataHolder
{
    public static final  ApplicationDataHolder inst = new ApplicationDataHolder();

    public int CurrentConnectionID =-1;
    public List<DataBaseConnection> Connections;
}
