package com.zwk.tool.exceller.unit;

import com.zwk.tool.exceller.service.UnmarshallerService;
import com.zwk.tool.exceller.service.UnmarshallerServiceImpl;
import com.zwk.tool.exceller.util.Fixture;
import com.zwk.tool.exceller.util.object.Example;
import com.zwk.tool.exceller.util.object.Example2;
import com.zwk.tool.exceller.util.object.Example3;
import com.zwk.tool.exceller.util.object.Example4;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UnmarshallerServiceImplTest {

    private static final List<Example> expectedExamples;
    private static final List<Example2> expectedExample2s;
    private static final List<Example3> expectedExample3s;
    private static final List<Example4> expectedExample4s;

    static {
        Example example1 = new Example(new Date(2017-1900, 7, 11), 2, "1.25");
        Example example2 = new Example(new Date(2017-1900, 10, 11, 2, 52, 48), 1, "43050.12");
        expectedExamples = Arrays.asList(example1, example2);

        Example2 example21 = new Example2(new Date(2017-1900, 7, 11), 2, "1.25");
        Example2 example22 = new Example2(new Date(2017-1900, 10, 11, 2, 52, 48), 1, "43050.12");
        expectedExample2s = Arrays.asList(example21, example22);

        Example3 example31 = new Example3(new Date(2017-1900, 7, 11), 2, "1.25");
        Example3 example32 = new Example3(new Date(2017-1900, 10, 11, 2, 52, 48), 1, "43050.12");
        expectedExample3s = Arrays.asList(example31, example32);

        Example4 example41 = new Example4(new Date(2017-1900, 7, 11), 2, "1.25");
        Example4 example42 = new Example4(new Date(2017-1900, 10, 11, 2, 52, 48), 1, "43050.12");
        expectedExample4s = Arrays.asList(example41, example42);
    }

    @Test
    public void fromExcelWithoutExcelSheetAnnotationSuccess () throws Exception {

        InputStream file = Fixture.getFile("excel/test1.xlsx");

        UnmarshallerService unmarshallerService = new UnmarshallerServiceImpl();
        List<Example> examples = unmarshallerService.fromExcel(file, Example.class);


        Assert.assertEquals(expectedExamples, examples);

    }

    @Test
    public void fromExcelWithExcelSheetIndexAnnotationSuccess () throws Exception {

        InputStream file = Fixture.getFile("excel/test2.xlsx");

        UnmarshallerService unmarshallerService = new UnmarshallerServiceImpl();
        List<Example2> examples = unmarshallerService.fromExcel(file, Example2.class);


        Assert.assertEquals(expectedExample2s, examples);

    }

    @Test
    public void fromExcelWithExcelSheetNameAnnotationSuccess () throws Exception {

        InputStream file = Fixture.getFile("excel/test2.xlsx");

        UnmarshallerService unmarshallerService = new UnmarshallerServiceImpl();
        List<Example3> examples = unmarshallerService.fromExcel(file, Example3.class);


        Assert.assertEquals(expectedExample3s, examples);

    }

    @Test
    public void fromExcelWithExcelSheetNameAndIndexAnnotationSuccess () throws Exception {

        InputStream file = Fixture.getFile("excel/test2.xlsx");

        UnmarshallerService unmarshallerService = new UnmarshallerServiceImpl();
        List<Example4> examples = unmarshallerService.fromExcel(file, Example4.class);


        Assert.assertEquals(expectedExample4s, examples);

    }
}
