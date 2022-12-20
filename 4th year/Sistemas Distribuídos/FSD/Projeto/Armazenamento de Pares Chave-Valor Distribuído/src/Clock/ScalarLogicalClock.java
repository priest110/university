package Clock;

public class ScalarLogicalClock
{
    private int clock;

    public ScalarLogicalClock() { this.clock = 0; }

    public synchronized void local_event() { this.clock++; }

    public synchronized void remote_event(int msg_clock) { this.clock = (this.clock <= msg_clock ? msg_clock : this.clock) + 1; }

    public int getLTime() { return this.clock; }
}
