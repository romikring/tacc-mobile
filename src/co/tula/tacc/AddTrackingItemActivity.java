package co.tula.tacc;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import co.tula.tacc.entity.Project;
import co.tula.tacc.tables.ProjectsTable;
import co.tula.tacc.tables.TasksTable;

public class AddTrackingItemActivity extends Activity
{
	ProjectsTable projectsTable;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tracking_item_activity);
        
        projectsTable = new ProjectsTable();
        
        ArrayList<Project> projects = projectsTable.getAllProjects();
        
        final Spinner spinner = (Spinner) findViewById(R.id.customer_spinner);
        ArrayAdapter<Project> aa = new ArrayAdapter<Project>(
    		this, android.R.layout.simple_spinner_dropdown_item, projects
		);
        
        spinner.setAdapter(aa);
        
        final EditText desc = (EditText) findViewById(R.id.task_description);
        
        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String description = desc.getText().toString();
				if (description.length() < 3) {
					// TODO: warning dialog
					return;
				}
				
				final Project project = (Project) spinner.getSelectedItem();
				if (project == null) {
					// TODO: warning dialog
					return;
				}
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						TasksTable tasksTable = new TasksTable();
						
						// TODO: Sync with task list on parent activity
						tasksTable.addTask(project.getTaccId(), description);
					}
				}).start();
				
				finish();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_tracking_item_activity, menu);
        return true;
    }
}