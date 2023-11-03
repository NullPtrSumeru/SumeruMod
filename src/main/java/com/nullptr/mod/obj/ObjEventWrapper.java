package com.lycanitesmobs.client.obj;

import com.nullptr.mod.obj.ObjEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ObjEventWrapper extends Event
{

    public ObjEvent objEvent;

    public ObjEventWrapper(ObjEvent e)
    {
        this.objEvent = e;
    }

    public boolean isCancelable()
    {
        return objEvent.canBeCancelled();
    }
}