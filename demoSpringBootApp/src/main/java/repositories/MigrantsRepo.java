package repositories;


import java.util.ArrayList;
import java.util.List;

import com.univocity.parsers.common.DataProcessingException;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.RetryableErrorHandler;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import it.univpm.demoSpringBootApp.models.MigrationStatus;
import it.univpm.demoSpringBootApp.services.TSVReader;

public class MigrantsRepo {
	private static final String filename = "datafile.tsv";
	private static List<MigrationStatus> migr_changesList =  new ArrayList<MigrationStatus>();
	
	public MigrantsRepo()
	{
		try		
		{
		BeanListProcessor<MigrationStatus> rowProcessor = new BeanListProcessor<MigrationStatus>(MigrationStatus.class);
		TsvParserSettings parserSettings = new TsvParserSettings();
		parserSettings.setRowProcessor(rowProcessor);
		parserSettings.getFormat().setLineSeparator("\n");
		parserSettings.setHeaderExtractionEnabled(true);
		parserSettings.setProcessorErrorHandler(new RetryableErrorHandler<ParsingContext>() {	
			
		    @Override
		    public void handleError(DataProcessingException error, Object[] inputRow, ParsingContext context) {
		     		            getDefaultValue();		
		        }
		    });
		TsvParser parser = new TsvParser(parserSettings);
		parser.parse(new TSVReader(filename).getReader()); 
		migr_changesList= rowProcessor.getBeans();		

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
