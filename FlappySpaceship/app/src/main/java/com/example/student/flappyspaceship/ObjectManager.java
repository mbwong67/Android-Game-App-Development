package com.example.student.flappyspaceship;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathon Tyson on 16/02/2018.
 */

public class ObjectManager
{
    // Make it a singleton
    private static ObjectManager instance;

    public ArrayList<GameObject> m_allObjectList = new ArrayList<GameObject>();
    public ArrayList<GameObject> m_colliderList = new ArrayList<GameObject>();
    public ArrayList<GameObject> m_enemyList = new ArrayList<GameObject>();

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

    public void AddEnemy(GameObject enemy)
    {
        m_enemyList.add(enemy);
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
            if (m_allObjectList.get(i).IsActive())
            {
                m_allObjectList.get(i).Update();
            }
        }
    }

    public void DrawAll()
    {
        for (int i = 0; i < m_allObjectList.size(); i++)
        {
            m_allObjectList.get(i).Draw();
        }
    }

    public void DeleteInactiveItems()
    {
        for (int i = 0; i < m_allObjectList.size(); ++i)
        {
            if (!m_allObjectList.get(i).IsActive())
            {
                m_allObjectList.remove(i);
            }
        }

        for (int i = 0; i < m_colliderList.size(); ++i)
        {
            if (!m_colliderList.get(i).IsActive())
            {
                m_colliderList.remove(i);
            }
        }
    }

    public void ProcessCollisions()
    {
        for (int i = 0; i < m_colliderList.size(); ++i)
        {
            for (int j = 1; j < m_colliderList.size(); ++j)
            {
                if (m_colliderList.get(i).IsActive() && m_colliderList.get(j).IsActive())
                {
                    // if (m_colliderList.get(i).HasCollided(m_allObjectList.get(j)))
                    if((Rect.intersects(m_colliderList.get(i).getHitbox(), m_colliderList.get(j).getHitbox())))
                    {
                        m_colliderList.get(i).ProcessCollision(m_colliderList.get(j));
                        m_colliderList.get(j).ProcessCollision(m_colliderList.get(i));
                    }
                }
            }
        }
    }
}
