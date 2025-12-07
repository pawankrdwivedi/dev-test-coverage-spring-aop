package test;

import app.Calculator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import app.Application;
import mapping.ExecutionTracker;
import org.junit.jupiter.api.*;

public class CalculatorTest {

    static AnnotationConfigApplicationContext ctx;
    Calculator calc;

    @BeforeAll
    static void beforeAll() {
        ctx = new AnnotationConfigApplicationContext(Application.class);
    }

    @BeforeEach
    void init(TestInfo info) {
        //calc = ctx.getBean(Calculator.class);
        ExecutionTracker.setCurrentTest(info.getDisplayName());
    }

    @Test
    void test_Add() {
        calc = ctx.getBean(Calculator.class);
        assert calc.dev_add(2, 3) == 5;
    }

    @Test
    void test_Subtract() {
        calc = ctx.getBean(Calculator.class);
        assert calc.dev_subtract(5, 3) == 2;
    }

    @Test
    void test_Subtract1() {
        calc = ctx.getBean(Calculator.class);
        assert calc.dev_subtract(6, 3) == 3;
    }

    @Test
    void test_SubtractMultiply() {
        calc = ctx.getBean(Calculator.class);
        int i=calc.dev_subtract(6, 3);
        assert calc.dev_multiply(i, 3) == 9;
    }

    @Test
    void test_junk_test_cases() {
        String name="IRIS Software";
        assert name=="IRIS Software";
    }

    @AfterAll
    static void dumpMapping() throws Exception {
        ExecutionTracker.writeReport("target/test-method-mapping.json");
        if (ctx != null) ctx.close();
    }
}
