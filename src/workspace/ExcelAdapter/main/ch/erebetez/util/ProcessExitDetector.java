/* *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  * 
 Code is from 
 http://beradrian.wordpress.com/2008/11/03/detecting-process-exit-in-java/
 
 Thanks Adrian
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  */
package ch.erebetez.util;

import java.util.*;

/**
 * Detects when a process is finished and invokes the associated listeners.
 */
public class ProcessExitDetector extends Thread {

    /** The process for which we have to detect the end. */
    private Process process;
    /** The associated listeners to be invoked at the end of the process. */
    private List<ProcessListener> listeners = new ArrayList<ProcessListener>();

    /**
     * Starts the detection for the given process
     * @param process the process for which we have to detect when it is finished
     */
    public ProcessExitDetector(Process process) {
        try {
            // test if the process is finished
            process.exitValue();
            throw new IllegalArgumentException("The process is already ended");
        } catch (IllegalThreadStateException exc) {
        	System.out.println("add process " + process.toString());
            this.process = process;
        }
    }

    /** @return the process that it is watched by this detector. */
    public Process getProcess() {
        return process;
    }

    public void run() {
        try {
        	System.out.println("run startet " + process.toString());
            
        	// wait for the process to finish
            process.waitFor();
            
            
            System.out.println("process beendet " + process.toString());
            // invokes the listeners
            for (ProcessListener listener : listeners) {
                listener.processFinished(process);
            }
        } catch (InterruptedException e) {
        	System.out.println("Error " + process.toString());
        }
    }

    /** Adds a process listener.
     * @param listener the listener to be added
     */
    public void addProcessListener(ProcessListener listener) {
        listeners.add(listener);
    }

    /** Removes a process listener.
     * @param listener the listener to be removed
     */
    public void removeProcessListener(ProcessListener listener) {
        listeners.remove(listener);
    }
}