package com.stanfy.enroscar.views.utils.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.view.View;
import android.widget.LinearLayout;

import com.stanfy.enroscar.views.GUIUtils;

/**
 * Tests for {@link GUIUtils}.
 */
@RunWith(RobolectricTestRunner.class)
public class GUIUtilsTest {

  @Test
  public void testFind() {
    final LinearLayout parent = new LinearLayout(Robolectric.application);
    parent.setId(2);
    final View testView = new View(Robolectric.application);
    testView.setId(3);
    parent.addView(testView);

    assertThat(GUIUtils.<LinearLayout>find(parent, 2), is(parent));
    assertThat(GUIUtils.find(parent, 3), is(testView));
  }

}
