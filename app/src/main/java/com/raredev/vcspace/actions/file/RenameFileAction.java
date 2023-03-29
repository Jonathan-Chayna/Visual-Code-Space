package com.raredev.vcspace.actions.file;

import android.view.LayoutInflater;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.raredev.vcspace.R;
import com.raredev.vcspace.actions.ActionData;
import com.raredev.vcspace.databinding.LayoutTextinputBinding;
import com.raredev.vcspace.fragments.FileTreeFragment;
import com.unnamed.b.atv.model.TreeNode;
import java.io.File;

public class RenameFileAction extends FileAction {

  @Override
  public boolean isApplicable(File file) {
    return true;
  }

  @Override
  public void performAction(@NonNull ActionData data) {
    FileTreeFragment fragment = (FileTreeFragment) data.get(FileTreeFragment.class);
    TreeNode node = (TreeNode) data.get(TreeNode.class);

    LayoutTextinputBinding binding =
        LayoutTextinputBinding.inflate(LayoutInflater.from(fragment.requireActivity()));
    EditText et_filename = binding.etInput;
    binding.tvInputLayout.setHint(R.string.rename_hint);

    et_filename.setText(node.getValue().getName());

    new MaterialAlertDialogBuilder(fragment.requireActivity())
        .setTitle(R.string.rename)
        .setPositiveButton(
            R.string.rename,
            (dlg, i) -> {
              File newFile = new File(node.getValue().getAbsolutePath());

              if (!et_filename.getText().toString().equals(node.getValue().getName())) {
                if (newFile.renameTo(
                    new File(node.getValue().getParentFile(), et_filename.getText().toString().trim()))) {
                  TreeNode parent = node.getParent();
                  if (parent != null) {
                    fragment.listNode(
                        parent,
                        () -> {
                          fragment.expandNode(parent);
                        });
                  }
                  fragment.updateRootNode(newFile);
                }
              }
            })
        .setNegativeButton(R.string.cancel, null)
        .setView(binding.getRoot())
        .show();
  }

  @Override
  public int getIcon() {
    return R.drawable.file_rename;
  }

  @Override
  public int getTitle() {
    return R.string.rename;
  }
}
