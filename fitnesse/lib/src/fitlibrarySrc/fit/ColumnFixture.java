// Modified or written by Object Mentor, Inc. for inclusion with FitNesse.
// Copyright (c) 2002 Cunningham & Cunningham, Inc.
// Released under the terms of the GNU General Public License version 2 or later.
package fit;

// Copyright (c) 2002 Cunningham & Cunningham, Inc.
// Released under the terms of the GNU General Public License version 2 or later.

public class ColumnFixture extends Fixture {

	public Binding columnBindings[];
	protected boolean hasExecuted = false;

	@Override
	public void doRows(Parse rows) {
		bind(rows.parts);
		super.doRows(rows.more);
	}

	@Override
	public void doRow(Parse row) {
		hasExecuted = false;
		try {
			reset();
			super.doRow(row);
			if (!hasExecuted) {
				execute();
			}
		} catch (Exception e) {
			exception(row.leaf(), e);
		}
	}

	@Override
	public void doCell(Parse cell, int column) {
		try {
			columnBindings[column].doCell(this, cell);
		} catch (Throwable e) {
			exception(cell, e);
		}
	}

	@Override
	public void check(Parse cell, TypeAdapter a) {
		try {
			executeIfNeeded();
		} catch (Exception e) {
			exception(cell, e);
		}
		super.check(cell, a);
	}

	protected void executeIfNeeded() throws Exception {
		if (!hasExecuted) {
			hasExecuted = true;
			execute();
		}
	}

	public void reset() throws Exception {
		// about to process first cell of row
	}

	public void execute() throws Exception {
		// about to process first method call of row
	}

	public void bind(Parse headsInitial) {
		Parse heads = headsInitial;
		try {
			columnBindings = new Binding[heads.size()];
			for (int i = 0; heads != null; i++, heads = heads.more) {
				columnBindings[i] = createBinding(i, heads);
			}
		} catch (Throwable throwable) {
			exception(heads, throwable);
		}
	}

	@SuppressWarnings("unused")
	protected Binding createBinding(int column, Parse heads) throws Throwable {
		return Binding.create(this, heads.text());
	}
}
