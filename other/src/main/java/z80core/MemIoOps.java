// 
// Decompiled by Procyon v0.5.36
// 

package z80core;

public interface MemIoOps
{
    int fetchOpcode(final int p0);
    
    int peek8(final int p0);
    
    void poke8(final int p0, final int p1);
    
    int peek16(final int p0);
    
    void poke16(final int p0, final int p1);
    
    int inPort(final int p0);
    
    void outPort(final int p0, final int p1);
    
    void contendedStates(final int p0, final int p1);
    
    void execDone(final int p0);
}
