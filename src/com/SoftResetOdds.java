package com;

import com.data.Lists;
import com.ivs.Checker;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.internal.cocoa.NSWindow;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.mihalis.opal.infinitePanel.InfiniteProgressPanel;
import org.mihalis.opal.multiChoice.MultiChoice;

public class SoftResetOdds {

	protected Shell shell;

	protected Composite compositeIVs;
	protected Label lblIvs;
	protected Label lblMin;
	protected Label lblMax;
	protected Label lblHP;
	protected Label lblAtt;
	protected Label lblDef;
	protected Label lblSpA;
	protected Label lblSpD;
	protected Label lblSpe;
	protected Spinner spinnerHPMin;
	protected Spinner spinnerAttMin;
	protected Spinner spinnerDefMin;
	protected Spinner spinnerSpAMin;
	protected Spinner spinnerSpDMin;
	protected Spinner spinnerSpeMin;
	protected Spinner spinnerHPMax;
	protected Spinner spinnerAttMax;
	protected Spinner spinnerDefMax;
	protected Spinner spinnerSpAMax;
	protected Spinner spinnerSpDMax;
	protected Spinner spinnerSpeMax;

	protected Composite compositeMisc;
	protected Label lblNature;
	protected MultiChoice<String> comboNature;
	protected Label lblHiddenPowers;
	protected MultiChoice<String> comboHPs;
	protected Button btnSync;
	protected Button btnShiny;
	protected Button btnShinyCharm;

	protected Composite compositeChances;
	protected Text textOdds;
	protected Label lblChances;
	protected Button btnRun;

	private Checker checker;
	protected Button btnForcedIVs;
	protected Button btnCopyToClipboard;

	protected InfiniteProgressPanel iPP;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		final SoftResetOdds window = new SoftResetOdds();
		try {
			Display.getDefault().syncExec(new Runnable() {
			    public void run() {
			        window.open();
			    }
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		shell.pack();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public class SpinnerMinModifyListener implements ModifyListener {

		private Spinner current;
		private Spinner partner;

		@Override
		public void modifyText(ModifyEvent arg0) {
			if( current.getSelection() > partner.getSelection() ) {
				partner.setSelection( current.getSelection() );
			}
		}

		public void setCurrent( Spinner curr ) { current = curr; }
		public void setPartner( Spinner part ) { partner = part; }

	}

	public class SpinnerMaxModifyListener implements ModifyListener {

		private Spinner current;
		private Spinner partner;

		@Override
		public void modifyText(ModifyEvent arg0) {
			if( current.getSelection() < partner.getSelection() ) {
				partner.setSelection( current.getSelection() );
			}
		}

		public void setCurrent( Spinner curr ) { current = curr; }
		public void setPartner( Spinner part ) { partner = part; }

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		checker = new Checker();
		checker.setPrintMatches(false);
		shell = new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
		NSWindow nswindow = shell.view.window();
		nswindow.setCollectionBehavior(0);
		//nswindow.setShowsResizeIndicator(false);

		shell.setFullScreen(false);
		shell.setSize(370, 342);
		shell.setText("Soft Reset Odds Calculator");
		shell.setLayout(new GridLayout(1, false));
		
		iPP = InfiniteProgressPanel.getInfiniteProgressPanelFor(shell);
		/*
		 * iPP.setText("Running...");
		 * iPP.setTextColor(shell.getDisplay().getSystemColor(SWT.COLOR_DARK_BLUE));
		 * iPP.setTextFont(new Font(shell.getDisplay(), "Courier New", 18, SWT.BOLD));
		*/
		{
			compositeIVs = new Composite(shell, SWT.NONE);
			compositeIVs.setLayout(new GridLayout(7, false));
			{
				GridData gd_compositeIVs = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
				gd_compositeIVs.heightHint = 102;
				compositeIVs.setLayoutData(gd_compositeIVs);
			}
			{
				lblIvs = new Label(compositeIVs, SWT.NONE);
				lblIvs.setBounds(0, 0, 60, 14);
				lblIvs.setText("IVs");
			}
			{
				lblHP = new Label(compositeIVs, SWT.NONE);
				lblHP.setAlignment(SWT.CENTER);
				lblHP.setText("HP");
				lblHP.addMouseListener( new MouseListener() {
					private int n = 2;
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {}
					@Override
					public void mouseDown(MouseEvent arg0) {}
					@Override
					public void mouseUp(MouseEvent arg0) {
						switch( n ) {
						case 0:
							spinnerHPMin.setSelection(0);
							n = 1;
							break;
						case 1:
							spinnerHPMin.setSelection(30);
							n = 2;
							break;
						case 2:
							spinnerHPMin.setSelection(31);
							n = 0;
							break;
						}
					}
				});
			}
			{
				lblAtt = new Label(compositeIVs, SWT.NONE);
				lblAtt.setAlignment(SWT.CENTER);
				lblAtt.setText("Att");
				lblAtt.addMouseListener( new MouseListener() {
					private int n = 2;
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {}
					@Override
					public void mouseDown(MouseEvent arg0) {}
					@Override
					public void mouseUp(MouseEvent arg0) {
						switch( n ) {
						case 0:
							spinnerAttMin.setSelection(0);
							n = 1;
							break;
						case 1:
							spinnerAttMin.setSelection(30);
							n = 2;
							break;
						case 2:
							spinnerAttMin.setSelection(31);
							n = 0;
							break;
						}
					}
				});
			}
			{
				lblDef = new Label(compositeIVs, SWT.NONE);
				lblDef.setAlignment(SWT.CENTER);
				lblDef.setText("Def");
				lblDef.addMouseListener( new MouseListener() {
					private int n = 2;
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {}
					@Override
					public void mouseDown(MouseEvent arg0) {}
					@Override
					public void mouseUp(MouseEvent arg0) {
						switch( n ) {
						case 0:
							spinnerDefMin.setSelection(0);
							n = 1;
							break;
						case 1:
							spinnerDefMin.setSelection(30);
							n = 2;
							break;
						case 2:
							spinnerDefMin.setSelection(31);
							n = 0;
							break;
						}
					}
				});
			}
			{
				lblSpA = new Label(compositeIVs, SWT.NONE);
				lblSpA.setAlignment(SWT.CENTER);
				lblSpA.setText("SpA");
				lblSpA.addMouseListener( new MouseListener() {
					private int n = 2;
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {}
					@Override
					public void mouseDown(MouseEvent arg0) {}
					@Override
					public void mouseUp(MouseEvent arg0) {
						switch( n ) {
						case 0:
							spinnerSpAMin.setSelection(0);
							n = 1;
							break;
						case 1:
							spinnerSpAMin.setSelection(30);
							n = 2;
							break;
						case 2:
							spinnerSpAMin.setSelection(31);
							n = 0;
							break;
						}
					}
				});
			}
			{
				lblSpD = new Label(compositeIVs, SWT.NONE);
				lblSpD.setAlignment(SWT.CENTER);
				lblSpD.setText("SpD");
				lblSpD.addMouseListener( new MouseListener() {
					private int n = 2;
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {}
					@Override
					public void mouseDown(MouseEvent arg0) {}
					@Override
					public void mouseUp(MouseEvent arg0) {
						switch( n ) {
						case 0:
							spinnerSpDMin.setSelection(0);
							n = 1;
							break;
						case 1:
							spinnerSpDMin.setSelection(30);
							n = 2;
							break;
						case 2:
							spinnerSpDMin.setSelection(31);
							n = 0;
							break;
						}
					}
				});
			}
			{
				lblSpe = new Label(compositeIVs, SWT.NONE);
				lblSpe.setAlignment(SWT.CENTER);
				lblSpe.setText("Spe");
				lblSpe.addMouseListener( new MouseListener() {
					private int n = 2;
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {}
					@Override
					public void mouseDown(MouseEvent arg0) {}
					@Override
					public void mouseUp(MouseEvent arg0) {
						switch( n ) {
						case 0:
							spinnerSpeMin.setSelection(0);
							n = 1;
							break;
						case 1:
							spinnerSpeMin.setSelection(30);
							n = 2;
							break;
						case 2:
							spinnerSpeMin.setSelection(31);
							n = 0;
							break;
						}
					}
				});
			}
			{
				lblMin = new Label(compositeIVs, SWT.NONE);
				lblMin.setText("Min");
			}
			{
				spinnerHPMin = new Spinner(compositeIVs, SWT.BORDER);
				spinnerHPMin.setPageIncrement(5);
				spinnerHPMin.setMaximum(31);
				spinnerHPMin.setSelection(30);
			}
			{
				spinnerAttMin = new Spinner(compositeIVs, SWT.BORDER);
				spinnerAttMin.setPageIncrement(5);
				spinnerAttMin.setMaximum(31);
				spinnerAttMin.setSelection(30);
			}
			{
				spinnerDefMin = new Spinner(compositeIVs, SWT.BORDER);
				spinnerDefMin.setPageIncrement(5);
				spinnerDefMin.setMaximum(31);
				spinnerDefMin.setSelection(30);
			}
			{
				spinnerSpAMin = new Spinner(compositeIVs, SWT.BORDER);
				spinnerSpAMin.setPageIncrement(5);
				spinnerSpAMin.setMaximum(31);
				spinnerSpAMin.setSelection(30);
			}
			{
				spinnerSpDMin = new Spinner(compositeIVs, SWT.BORDER);
				spinnerSpDMin.setPageIncrement(5);
				spinnerSpDMin.setMaximum(31);
				spinnerSpDMin.setSelection(30);
			}
			{
				spinnerSpeMin = new Spinner(compositeIVs, SWT.BORDER);
				spinnerSpeMin.setPageIncrement(5);
				spinnerSpeMin.setMaximum(31);
				spinnerSpeMin.setSelection(30);
			}
			{
				lblMax = new Label(compositeIVs, SWT.NONE);
				lblMax.setText("Max");
			}
			{
				spinnerHPMax = new Spinner(compositeIVs, SWT.BORDER);
				spinnerHPMax.setPageIncrement(5);
				spinnerHPMax.setMaximum(31);
				spinnerHPMax.setSelection(31);
			}
			{
				spinnerAttMax = new Spinner(compositeIVs, SWT.BORDER);
				spinnerAttMax.setPageIncrement(5);
				spinnerAttMax.setMaximum(31);
				spinnerAttMax.setSelection(31);
			}
			{
				spinnerDefMax = new Spinner(compositeIVs, SWT.BORDER);
				spinnerDefMax.setPageIncrement(5);
				spinnerDefMax.setMaximum(31);
				spinnerDefMax.setSelection(31);
			}
			{
				spinnerSpAMax = new Spinner(compositeIVs, SWT.BORDER);
				spinnerSpAMax.setPageIncrement(5);
				spinnerSpAMax.setMaximum(31);
				spinnerSpAMax.setSelection(31);
			}
			{
				spinnerSpDMax = new Spinner(compositeIVs, SWT.BORDER);
				spinnerSpDMax.setPageIncrement(5);
				spinnerSpDMax.setMaximum(31);
				spinnerSpDMax.setSelection(31);
			}
			{
				spinnerSpeMax = new Spinner(compositeIVs, SWT.BORDER);
				spinnerSpeMax.setPageIncrement(5);
				spinnerSpeMax.setMaximum(31);
				spinnerSpeMax.setSelection(31);
			}

			{

				SpinnerMinModifyListener smmlhp = new SpinnerMinModifyListener();
				smmlhp.setCurrent(spinnerHPMin);
				smmlhp.setPartner(spinnerHPMax);
				spinnerHPMin.addModifyListener( smmlhp );

				SpinnerMinModifyListener smmlatt = new SpinnerMinModifyListener();
				smmlatt.setCurrent(spinnerAttMin);
				smmlatt.setPartner(spinnerAttMax);
				spinnerAttMin.addModifyListener( smmlatt );

				SpinnerMinModifyListener smmldef = new SpinnerMinModifyListener();
				smmldef.setCurrent(spinnerDefMin);
				smmldef.setPartner(spinnerDefMax);
				spinnerDefMin.addModifyListener( smmldef );

				SpinnerMinModifyListener smmlspa = new SpinnerMinModifyListener();
				smmlspa.setCurrent(spinnerSpAMin);
				smmlspa.setPartner(spinnerSpAMax);
				spinnerSpAMin.addModifyListener( smmlspa );

				SpinnerMinModifyListener smmlspd = new SpinnerMinModifyListener();
				smmlspd.setCurrent(spinnerSpDMin);
				smmlspd.setPartner(spinnerSpDMax);
				spinnerSpDMin.addModifyListener( smmlspd );

				SpinnerMinModifyListener smmlspe = new SpinnerMinModifyListener();
				smmlspe.setCurrent(spinnerSpeMin);
				smmlspe.setPartner(spinnerSpeMax);
				spinnerSpeMin.addModifyListener( smmlspe );


				SpinnerMaxModifyListener smxmlhp = new SpinnerMaxModifyListener();
				smxmlhp.setCurrent(spinnerHPMax);
				smxmlhp.setPartner(spinnerHPMin);
				spinnerHPMax.addModifyListener( smxmlhp );

				SpinnerMaxModifyListener smxmlatt = new SpinnerMaxModifyListener();
				smxmlatt.setCurrent(spinnerAttMax);
				smxmlatt.setPartner(spinnerAttMin);
				spinnerAttMax.addModifyListener( smxmlatt );

				SpinnerMaxModifyListener smxmldef = new SpinnerMaxModifyListener();
				smxmldef.setCurrent(spinnerDefMax);
				smxmldef.setPartner(spinnerDefMin);
				spinnerDefMax.addModifyListener( smxmldef );

				SpinnerMaxModifyListener smxmlspa = new SpinnerMaxModifyListener();
				smxmlspa.setCurrent(spinnerSpAMax);
				smxmlspa.setPartner(spinnerSpAMin);
				spinnerSpAMax.addModifyListener( smxmlspa );

				SpinnerMaxModifyListener smxmlspd = new SpinnerMaxModifyListener();
				smxmlspd.setCurrent(spinnerSpDMax);
				smxmlspd.setPartner(spinnerSpDMin);
				spinnerSpDMax.addModifyListener( smxmlspd );

				SpinnerMaxModifyListener smxmlspe = new SpinnerMaxModifyListener();
				smxmlspe.setCurrent(spinnerSpeMax);
				smxmlspe.setPartner(spinnerSpeMin);
				spinnerSpeMax.addModifyListener( smxmlspe );
			}
		}
		{
			compositeMisc = new Composite(shell, SWT.NONE);
			compositeMisc.setLayout(new GridLayout(4, false));
			{
				GridData gd_compositeMisc = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
				gd_compositeMisc.widthHint = 360;
				gd_compositeMisc.heightHint = 58;
				compositeMisc.setLayoutData(gd_compositeMisc);
			}
			{
				lblNature = new Label(compositeMisc, SWT.NONE);
				lblNature.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblNature.setText("Nature");
				lblNature.addMouseListener( new MouseListener() {
					private boolean lastWasAll = true;
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {}
					@Override
					public void mouseDown(MouseEvent arg0) {}
					@Override
					public void mouseUp(MouseEvent arg0) {
						if( lastWasAll ) {
							comboNature.deselectAll();
						} else {
							comboNature.selectAll();
						}
						lastWasAll = !lastWasAll;
					}
				});
			}
			{
				comboNature = new MultiChoice<String>( compositeMisc , SWT.READ_ONLY );
				{
					GridData gd_comboNature = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
					gd_comboNature.widthHint = 88;
					comboNature.setLayoutData(gd_comboNature);
					String[] natureItems = new String[25];
					for( int i=0; i<Lists.NATURES.length; i++ ) {
						natureItems[i] = Lists.NATURES[i];
					}
					comboNature.addAll(natureItems);
					comboNature.selectAll();
				}
			}
			{
				btnSync = new Button(compositeMisc, SWT.CHECK);
				btnSync.setText("Synchronizer");
				btnSync.setSelection(true);
			}
			{
				btnForcedIVs = new Button(compositeMisc, SWT.CHECK);
				btnForcedIVs.setText("Forced IVs");
				btnForcedIVs.setSelection(true);
			}

			String[] HPItems = new String[16];
			for( int i=0; i<Lists.HPs.length; i++ ) {
				HPItems[i] = Lists.HPs[i];
			}
			{
				lblHiddenPowers = new Label(compositeMisc, SWT.NONE);
				lblHiddenPowers.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				lblHiddenPowers.setText("HPs");
				lblHiddenPowers.addMouseListener( new MouseListener() {
					private boolean lastWasAll = true;
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {}
					@Override
					public void mouseDown(MouseEvent arg0) {}
					@Override
					public void mouseUp(MouseEvent arg0) {
						if( lastWasAll ) {
							comboHPs.deselectAll();
						} else {
							comboHPs.selectAll();
						}
						lastWasAll = !lastWasAll;
					}
				});
			}
			comboHPs = new MultiChoice<String>(compositeMisc , SWT.READ_ONLY);
			
						comboHPs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
						comboHPs.addAll(HPItems);
						comboHPs.selectAll();
			{
				btnShiny = new Button(compositeMisc, SWT.CHECK);
				btnShiny.setText("Shiny");
			}
			{
				btnShinyCharm = new Button(compositeMisc, SWT.CHECK);
				btnShinyCharm.setText("Shiny Charm");
			}
		}
		{
			compositeChances = new Composite(shell, SWT.NONE);
			compositeChances.setLayout(new GridLayout(2, false));
			{
				GridData gd_compositeChances = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
				gd_compositeChances.widthHint = 360;
				compositeChances.setLayoutData(gd_compositeChances);
			}
			{
				lblChances = new Label(compositeChances, SWT.NONE);
				lblChances.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
				lblChances.setText("Chances");
			}
			{
				textOdds = new Text(compositeChances, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI);
				{
					GridData gd_textOdds = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
					gd_textOdds.heightHint = 82;
					textOdds.setLayoutData(gd_textOdds);
				}
			}
			{
				btnCopyToClipboard = new Button(compositeChances, SWT.NONE);
				{
					GridData gd_btnCopyToClipboard = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
					gd_btnCopyToClipboard.widthHint = 152;
					btnCopyToClipboard.setLayoutData(gd_btnCopyToClipboard);
				}
				btnCopyToClipboard.setText("Copy To Clipboard");
				
				btnCopyToClipboard.addListener( SWT.Selection , new Listener() {
					
					@Override
					public void handleEvent(Event arg0) {
						StringSelection ss = new StringSelection(textOdds.getText());
						Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
						clip.setContents( ss , null );
					}
					
				});
			}
			{
				btnRun = new Button(compositeChances, SWT.NONE);
				btnRun.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				btnRun.setText("Run");

				btnRun.addListener( SWT.Selection , new Listener() {

					@Override
					public void handleEvent(Event arg0) {
						iPP.start();
						int[][] ranges = new int[][] {
								{spinnerHPMin.getSelection()  , spinnerHPMax.getSelection() },
								{spinnerAttMin.getSelection() , spinnerAttMax.getSelection()},
								{spinnerDefMin.getSelection() , spinnerDefMax.getSelection()},
								{spinnerSpAMin.getSelection() , spinnerSpAMax.getSelection()},
								{spinnerSpDMin.getSelection() , spinnerSpDMax.getSelection()},
								{spinnerSpeMin.getSelection() , spinnerSpeMax.getSelection()},
						};
						checker.setRanges(ranges);

						boolean[] hps = new boolean[16];
						int[] sel = comboHPs.getSelectedIndex();
						for( int i : sel ) {
							hps[ Lists.HPNumbers[i] ] = true;
						}
						checker.setHPs(hps);

						if( btnForcedIVs.getSelection() ) {
							checker.run();
						} else {
							checker.runNoFixedIVs();
						}

						int NN = 0; int ND = 0;
						if( btnSync.getSelection() ) {
							ND = 50;
							NN   =  comboNature.getSelectedIndex().length==0  ?
									0 :
										comboNature.getSelectedIndex().length+25;
						} else {
							ND = 25;
							NN   = comboNature.getSelectedIndex().length;
						}

						int SD = btnShiny.getSelection() ? 4096 : 1;
						int SN = btnShiny.getSelection() ? (btnShinyCharm.getSelection() ? 3 : 1) : 1;

						double nIn = (checker.matches()*NN*SN)==0  ?  -1.0d : 
							(double)checker.total()*(double)ND*(double)SD /
							((double)checker.matches()*(double)NN*(double)SN);

						String txt = "IVS: " + checker.matches() + " / " + checker.total() +
								"\nNature: " +  NN + " / " + ND +
								"\nShiny: " + SN + " / " + SD + 
								"\n\nCombined: 1 in " + String.format("%,.2f",nIn);

						textOdds.setText( txt );
						iPP.stop();
					}

				});
			}
		}

	}
}
