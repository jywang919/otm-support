package com.ori.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SineWaveHelper {

    // 0.9813828 - Float
    // 0.49620770714687745 - Double

	private static final Logger LOG = LoggerFactory.getLogger(SineWaveHelper.class);
	static double PI =  3.1415962535897;
    static final Double CONST_PHASE = PI / 8;
    Double a = null;
    Double b = null;
    Double c = null;
    final double[] fyi = {
        0, 0, 0
    };
    SineWaveData in = null;

    Double omega = null;
    final SineWaveResultsData res = new SineWaveResultsData();

    final double [][] ri = {
        {
            0, 0, 0
        }, {
            0, 0, 0
        }, {
            0, 0, 0
        }
    };
    public SineWaveHelper() throws IOException {
        in = new SineWaveData();
        a = in.getAmp() * Math.cos(SineWaveHelper.CONST_PHASE);
        b = in.getAmp() * Math.sin(SineWaveHelper.CONST_PHASE);
        c = Double.valueOf(in.getOffset());
        calcInternal();
    }


    public SineWaveHelper(final SineWaveData data) {
        in = data;
    }

    public SineWaveResultsData calc() throws IOException {
        // return some fake data
        calcInternal();
        return res;
    }

    private double[] calcHelperSineWaveMatrixSine1(final double[] y) {
        final double[] abc = new double[3];
        final double[] ff = new double[3];
        // TODO put Ri = [0 0 0, 0 0 0; 0 0 0] into a matrix

        BlockRealMatrix mRi = new BlockRealMatrix(ri);
//        final RealMatrix inverse = MatrixUtils.inverse(mRi);
        RealMatrix mFyi = MatrixUtils.createColumnRealMatrix(fyi);

        for(int i=0;i<in.getN()-1;i++){

            ff[0] = (float) Math.sin(omega * i);
            ff[1] = (float) Math.cos(omega * i);
            ff[2] = 1;
            // put ff to matrix
            final RealMatrix mFf = MatrixUtils
                    .createColumnRealMatrix(ff);
            final RealMatrix tFf = mFf.transpose();
            // calc FF2= ff*ff'
            final RealMatrix mFf2 = mFf.multiply(tFf);
            // cal Ri = Ri + FF2
            mRi = mRi.add(mFf2);
            // cal FYi = FYi _ff*littleY
            mFyi = mFyi.add(mFf.scalarMultiply(y[i]));
        }

        final BlockRealMatrix invRi = (BlockRealMatrix) MatrixUtils.inverse(mRi);
        final BlockRealMatrix mTeta1 = invRi.multiply(mFyi);

        abc[0] = mTeta1.getData()[0][0];
        abc[1] = mTeta1.getData()[1][0];
        abc[2] = mTeta1.getData()[2][0];

        return abc;
    }

    private void calcInternal() throws IOException {
        omega = 2 * PI * in.getFrequency();  //   CONST_PHASE = 3.1415962535897 / 8;
        // if (in.isDefault()) {
        // res.getTeta1()[0] = a;
        // res.getTeta1()[1] = b;
        // res.getTeta1()[2] = c;
        // return;
        // }

        final double[] y = new double[in.getN()];
        for (int i = 0; i < in.getN(); i++ ) {
            y[i] = (double) (in.getAmp() * Math.sin(omega * in.getX()[i] + SineWaveHelper.CONST_PHASE) + in.getOffset());
        }

        /**
         * figure(1)
         * plot(X,y,'b')  //X [0:300]   Y: [Amp*Sin(Omega*X + Phase) + Offset
         * legend('SINE')
         * xlabel('Time (sec)')
         * ylabel('Sine')
         */
           
//        String chartName = chartDemo(y);
        String chartName = chartSineWave(y);
        
        final Double a = in.getAmp() * Math.cos(SineWaveHelper.CONST_PHASE);
        final Double b = in.getAmp() * Math.sin(SineWaveHelper.CONST_PHASE);
        final Double c = in.getOffset();

        final Double[] teta = new Double[3];
        teta[0] = a;
        teta[1] = b;
        teta[2] = c;        

        // now call HelperSineWaveMatrixSine1 to do [a b c] = matrix_sin_1(y, n,
        // omega)
        final double[] abc = calcHelperSineWaveMatrixSine1(y);

        res.getTeta1()[0] = abc[0];
        res.getTeta1()[1] = abc[1];
        res.getTeta1()[2] = abc[2];
        res.setChartName(chartName);
    }
    
    private String chartSineWave(double[] y) throws IOException {

        // Create some sample data
        double[] x = new double[in.getX().length];
        
        for(int i = 1; i < x.length; i++){
            x[i] = Double.valueOf(in.getX()[i]);
//            SineWaveHelper.LOG.debug("SineWaveHelper - i " + i + " x/y "+ x[i]+ "/"+y[i]);               
        }

        // JAVA:                             // MATLAB:
        SineWaveChart fig = new SineWaveChart(); // figure('Position',[100 100 640 480]);
        fig.plot(x, y, "-r", 2.0f, "Sine over Time"); // plot(x,y1,'-r','LineWidth',2);       public void plot(double[] x, double[] y, String spec, float lineWidth, String title) {
//        fig.plot(x, y2, ":k", 3.0f, "BAC");  // plot(x,y2,':k','LineWidth',3);
        fig.RenderPlot();                    // First render plot before modifying
        fig.title("Sine Wave (Amp: " +in.getAmp()+" Freq: "+in.getFrequency()+" Off: "+in.getOffset()+")" );    // title('Stock 1 vs. Stock 2');
        fig.xlim(0, in.getN());                   // xlim([10 100]);
        fig.ylim(-1.2*in.getAmp(), 1.2*in.getAmp());                  // ylim([200 300]);
        
        fig.xlabel("Time (sec.)");                  // xlabel('Days');
        fig.ylabel("Sine");                 // ylabel('Price');
        fig.grid("on","on");                 // grid on;
        fig.legend("northeast");             // legend('AAPL','BAC','Location','northeast')
        fig.font("Helvetica",15);            // .. 'FontName','Helvetica','FontSize',15
        String name = calendarJpegName();
//        fig.saveas(name,640,480);   // saveas(gcf,'MyPlot','jpeg');
        
        BufferedImage image = fig.createBufferedImage(640, 480);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, "png", output);
        String b64 = DatatypeConverter.printBase64Binary(output.toByteArray());
        LOG.debug("image info: "+b64);
        res.setImage(b64);
        
        return name;
    }
    
    private String chartDemo() {

        // Create some sample data
        double[] x = new double[100]; x[0] = 1;
        double[] y1 = new double[100]; y1[0] = 200;
        double[] y2 = new double[100]; y2[0] = 300;
        for(int i = 1; i < x.length; i++){
            x[i] = i+1; 
            y1[i] = y1[i-1] + Math.random()*10 - 4;
            y2[i] = y2[i-1] + Math.random()*10 - 6;
        }

        // JAVA:                             // MATLAB:
        SineWaveChart fig = new SineWaveChart(); // figure('Position',[100 100 640 480]);
        fig.plot(x, y1, "-r", 2.0f, "AAPL"); // plot(x,y1,'-r','LineWidth',2);
        fig.plot(x, y2, ":k", 3.0f, "BAC");  // plot(x,y2,':k','LineWidth',3);
        fig.RenderPlot();                    // First render plot before modifying
        fig.title("Stock 1 vs. Stock 2");    // title('Stock 1 vs. Stock 2');
        fig.xlim(10, 100);                   // xlim([10 100]);
        fig.ylim(200, 300);                  // ylim([200 300]);
        fig.xlabel("Days");                  // xlabel('Days');
        fig.ylabel("Price");                 // ylabel('Price');
        fig.grid("on","on");                 // grid on;
        fig.legend("northeast");             // legend('AAPL','BAC','Location','northeast')
        fig.font("Helvetica",15);            // .. 'FontName','Helvetica','FontSize',15
        String name = calendarJpegName();
        fig.saveas(name,640,480);   // saveas(gcf,'MyPlot','jpeg');
        return name;
    }
    
    private String calendarJpegName(){
    	Calendar now = Calendar.getInstance();
    	int year = now.get(Calendar.YEAR);
    	int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
    	int day = now.get(Calendar.DAY_OF_MONTH);
    	int hour = now.get(Calendar.HOUR_OF_DAY);
    	int minute = now.get(Calendar.MINUTE);
    	int second = now.get(Calendar.SECOND);
    	int millis = now.get(Calendar.MILLISECOND);
    	
    	String re = "".concat(String.valueOf(year)).concat(String.valueOf(month)).concat(String.valueOf(day)
    			.concat(String.valueOf(hour))
    			.concat(String.valueOf(minute))
    			.concat(String.valueOf(second))
    			.concat(String.valueOf(millis))
    			.concat(".jpeg"));
    	return re;
    }
}
