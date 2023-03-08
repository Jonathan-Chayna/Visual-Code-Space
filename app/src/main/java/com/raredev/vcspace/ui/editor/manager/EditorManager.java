package com.raredev.vcspace.ui.editor.manager;

import android.content.Context;
import android.widget.ViewFlipper;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.raredev.common.Indexer;
import com.raredev.vcspace.R;
import com.raredev.vcspace.databinding.ActivityMainBinding;
import com.raredev.vcspace.ui.editor.CodeEditorView;
import com.raredev.vcspace.ui.editor.EditorViewModel;
import java.io.File;
import java.util.List;

public class EditorManager {
  private final String LAST_FILES_TAG = "lastOpenedFiles";

  private DrawerLayout drawerLayout;
  private ViewFlipper container;
  private TabLayout tabLayout;

  private Context context;

  private EditorViewModel viewModel;
  private Indexer indexer;

  public EditorManager(Context context, ActivityMainBinding binding) {
    this.context = context;

    this.drawerLayout = binding.drawerLayout;
    this.container = binding.container;
    this.tabLayout = binding.tabLayout;

    viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(EditorViewModel.class);
    indexer = new Indexer(context.getExternalFilesDir("editor") + "/oppenedFiles.json");
  }

  public EditorViewModel getViewModel() {
    return viewModel;
  }

  public void undo() {
    getCurrentEditor().undo();
  }

  public void redo() {
    getCurrentEditor().redo();
  }

  public void openRecentOpenedFiles() {
    if (viewModel.getFiles().getValue().isEmpty()) {
      for (File file : indexer.getList(LAST_FILES_TAG)) {
        if (!viewModel.contains(file) && file.exists()) {
          openFile(file, false);
        }
      }
    }
  }

  public void openFile(File file) {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.close();
    }
    openFile(file, true);
  }

  public void openFile(File file, boolean setCurrent) {
    if (!file.exists()) {
      return;
    }

    if (viewModel.contains(file)) {
      setCurrentPosition(viewModel.indexOf(file));
      return;
    }

    container.addView(new CodeEditorView(context, file));
    tabLayout.addTab(tabLayout.newTab().setText(file.getName()));

    viewModel.openFile(file);
    if (setCurrent) setCurrentPosition(viewModel.indexOf(file));
    saveOpenedFiles();
  }

  public void closeFile(int index) {
    if (index >= 0 && index < viewModel.getFiles().getValue().size()) {
      File file = viewModel.getFiles().getValue().get(index);
      if (file.exists()) {
        getEditorAtIndex(index).save();
      }

      getEditorAtIndex(index).release();
      viewModel.removeFile(index);
      tabLayout.removeTabAt(index);
      container.removeViewAt(index);
    }
    tabLayout.requestLayout();
  }

  public void closeOthers() {
    saveAllFiles(false);

    File editorFile = getEditorAtIndex(viewModel.getCurrentPosition()).getFile();
    for (int i = 0; i < viewModel.getFiles().getValue().size(); i++) {
      CodeEditorView editor = getEditorAtIndex(i);
      if (editor != null) {
        if (editorFile != viewModel.getFiles().getValue().get(i)) {
          closeFile(i);
        }
      }
    }
  }

  public void closeAllFiles() {
    if (!viewModel.getFiles().getValue().isEmpty()) {
      saveAllFiles(false);
      for (int i = 0; i < viewModel.getFiles().getValue().size(); i++) {
        getEditorAtIndex(i).release();
      }
      container.removeAllViews();
      tabLayout.removeAllTabs();
      viewModel.clear();
    }
  }

  public void saveAll(boolean showMsg) {
    saveAllFiles(showMsg);
    saveOpenedFiles();
  }

  public void saveAllFiles(boolean showMsg) {
    if (!viewModel.getFiles().getValue().isEmpty()) {
      for (int i = 0; i < viewModel.getFiles().getValue().size(); i++) {
        getEditorAtIndex(i).save();
      }

      if (showMsg) {
        ToastUtils.showShort(R.string.saved_files);
      }
    }
  }

  public void saveOpenedFiles() {
    try {
      indexer.put(LAST_FILES_TAG, viewModel.getFiles().getValue()).flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public CodeEditorView getEditorAtIndex(int index) {
    return (CodeEditorView) container.getChildAt(index);
  }

  public CodeEditorView getCurrentEditor() {
    return (CodeEditorView) container.getChildAt(viewModel.getCurrentPosition());
  }

  public void setCurrentPosition(int index) {
    final var tab = tabLayout.getTabAt(index);
    if (tab != null && index >= 0 && !tab.isSelected()) {
      tab.select();
    }
    viewModel.setCurrentPosition(index);
  }
}
