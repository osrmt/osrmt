package com.osframework.appclient.ui.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;

public class UICellEditor extends DefaultCellEditor {

	public UICellEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	public UICellEditor(JComboBox comboBox) {
		super(comboBox);
	}

	public UICellEditor(JTextField textField) {
		super(textField);
	}
	
	
}

