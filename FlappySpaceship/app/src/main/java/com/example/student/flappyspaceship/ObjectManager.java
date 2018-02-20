package com.example.student.flappyspaceship;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathon Tyson & Cristian Murarescu on 16/02/2018.
 */

public class ObjectManager
{
    // Make it a singleton
    private static ObjectManager instance;

    public ArrayList<GameObject> m_allObjectList = new ArrayList<GameObject>();
    public ArrayList<GameObject> m_colliderList = new ArrayList<GameObject>();

    private ObjectManager()
    {

    }

    public static ObjectManager GetInstance()
    {
        if(instance == null)
        {
            instance = new ObjectManager();
        }
        return instance;
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

    public void ObjectsCollision()
    {
        for (int i = 0; i < m_colliderList.size(); i++)
        {
            if (m_colliderList.get(i).GetActive() == false)
            {
                m_colliderList.remove(i);
                m_allObjectList.remove(i);
            }
        }
    }
}
