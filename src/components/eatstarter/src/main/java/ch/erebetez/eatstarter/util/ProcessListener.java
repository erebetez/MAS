/* *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 Code is from 
 http://beradrian.wordpress.com/2008/11/03/detecting-process-exit-in-java/
 
 Thanks Adrian
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  */
package ch.erebetez.eatstarter.util;

import java.util.EventListener;

public interface ProcessListener extends EventListener {
    void processFinished(Process process);
}