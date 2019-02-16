package com.osframework.framework.utility;

import java.util.LinkedList;

/**
* <p>ReaderWriter to lock service cache</p>
* Adapted from http://www.javaworld.com/javaworld/jw-04-1999/jw-04-toolbox_p.html
* 
* <br/>Trace: <a href="/functionality/rm/5695234.html">5695234</a>
*/
public class ReaderWriter {

private int activeReaders;     // = 0
private int waitingReaders;    // = 0
private int activeWriters;     // = 0

private final LinkedList<Object> writerLocks = new LinkedList<Object>();
 

/**
 * If no writers increment active readers and read
 */
public synchronized void requestRead()
{
    if( activeWriters==0 && writerLocks.size()==0 )
        ++activeReaders;
    else
    {   ++waitingReaders;
        try{ wait(); }catch(InterruptedException e){}
    }
}


/**
 * Decrement readers and let writers know
 */
public synchronized void readFinished()
{   if( --activeReaders == 0 )
	notifyWriters();
}


/**
 * If no readers or writers increment writers
 * otherwise wait
 *
 */
public void requestWrite()
{
    Object lock = new Object();
    synchronized( lock )
    {   synchronized( this )
        {   boolean okay_to_write = writerLocks.size()==0 
                                    && activeReaders==0
                                    && activeWriters==0;
            if( okay_to_write )
            {   ++activeWriters;
                return; // the "return" jumps over the "wait" call
            }
            writerLocks.addLast( lock );
        }
        try{ lock.wait(); } catch(InterruptedException e){}
    }
}

/**
 * Decrement writers and let readers or writers know
 *
 */
public synchronized void writeFinished()
{
    --activeWriters;
    if( waitingReaders > 0 )   // priority to waiting readers
        	notifyReaders();
        else
        	notifyWriters();
    }

/**
 * Reader notification
 *
 */ 
private void notifyReaders() { 
    activeReaders  += waitingReaders;
    waitingReaders = 0;
    notifyAll();
}

/**
 * Writer notification
 *
 */
private void notifyWriters() {                                   
    if( writerLocks.size() > 0 )
    {   
        Object oldest = writerLocks.removeFirst();
        ++activeWriters;
        synchronized( oldest ){ oldest.notify(); }
    }
}
 

}
