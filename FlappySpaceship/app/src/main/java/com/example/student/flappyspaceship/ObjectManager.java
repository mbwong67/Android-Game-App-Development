package com.example.student.flappyspaceship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathon Tyson & Cristian Murarescu on 16/02/2018.
 */

public class ObjectManager
{

    public ArrayList<GameObject> m_allObjectList = new ArrayList<GameObject>();
    public ArrayList<GameObject> m_colliderList = new ArrayList<GameObject>();

    public ObjectManager()
    {

    }

    public void AddItem(GameObject newItem, boolean collides)
    {
        m_allObjectList.add(newItem);

        if (collides)
        {
           m_colliderList.add(newItem);
        }
    }

    public void UpdateAll()
    {
        for (int i = 0; i < m_allObjectList.size(); i++)
        {
            m_allObjectList.get(i).Update();
        }
    }

    public void DrawAll()
    {
        for (int i = 0; i < m_allObjectList.size(); i++)
        {
            m_allObjectList.get(i).Draw();
        }
    }

    public void ProcessCollisions()
    {

    }



}
