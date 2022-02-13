package net.jet3.booking101.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataHandlerTest {

    @Test
    public void delete() {
        DataHandler dataHandler = new DataHandler("c18e9f51-94ba-461e-8959-232eb318d3f9");
        assertEquals(true, dataHandler.delete());
    }
}