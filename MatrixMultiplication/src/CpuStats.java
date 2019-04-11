import java.lang.management.ManagementFactory;

public class CpuStats
{
   private final long threadId;
   private long lastCpuTime = 0;
   private long lastPoll = 0;

   /**
    * Creates a CpuStats object for a single thread.
    * @param threadId The id of the thread to monitor
    * 
    */
   public CpuStats (long threadId)
   {
      this.threadId = threadId;
      lastCpuTime = getTotalTime ();
      lastPoll = System.nanoTime ();
   }

   /**
    * Creates a CpuStatus object for all threads. The supplied statistics affect
    * all threads in the current VM.
    */
   public CpuStats ()
   {
      threadId = -1;
      lastCpuTime = getTotalTime ();
      lastPoll = System.nanoTime ();
   }

   private long getRelativeTime ()
   {
      long currentCpuTime = getTotalTime ();
      long ret = currentCpuTime - lastCpuTime;
      lastCpuTime = currentCpuTime;

      return ret;
   }

   public double getUsage ()
   {
      long timeBefore = this.lastPoll;

      lastPoll = System.nanoTime ();
      long relTime = getRelativeTime ();

      return Math.max ((double)relTime / (double)(lastPoll - timeBefore), 0.0);
   }

   private long getTotalTime ()
   {
      if (threadId == -1)
      {
         long cpuTime = 0;
         for (long id : ManagementFactory.getThreadMXBean ().getAllThreadIds ())
         {
            cpuTime += ManagementFactory.getThreadMXBean ().getThreadCpuTime (id);
         }

         return cpuTime;
      }
      else
      {
         return ManagementFactory.getThreadMXBean ().getThreadCpuTime (threadId);
      }
   }
}