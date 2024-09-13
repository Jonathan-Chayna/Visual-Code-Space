/*
 * This file is part of Visual Code Space.
 *
 * Visual Code Space is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Visual Code Space is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Visual Code Space.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.teixeira.vcspace.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.teixeira.vcspace.PreferenceKeys
import com.teixeira.vcspace.R
import com.teixeira.vcspace.activities.PluginsActivity
import com.teixeira.vcspace.app.BaseApplication
import com.teixeira.vcspace.extensions.open

class SettingsFragment : PreferenceFragmentCompat() {

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.root_preferences, rootKey)

    findPreference<Preference>(PreferenceKeys.GENERAL_PREFERENCES)?.setOnPreferenceClickListener {
      findNavController().navigate(R.id.action_settingsFragment_to_generalSettingsFragment)
      true
    }

    findPreference<Preference>(PreferenceKeys.EDITOR_PREFERENCES)?.setOnPreferenceClickListener {
      findNavController().navigate(R.id.action_settingsFragment_to_editorSettingsFragment)
      true
    }

    findPreference<Preference>(PreferenceKeys.FILE_PREFERENCES)?.setOnPreferenceClickListener {
      findNavController().navigate(R.id.action_settingsFragment_to_fileSettingsFragment)
      true
    }

    findPreference<Preference>(PreferenceKeys.PLUGINS_PREFERENCES)?.setOnPreferenceClickListener {
      requireContext().open(PluginsActivity::class.java)
      true
    }

    findPreference<Preference>(PreferenceKeys.GITHUB_ABOUT_PREFERENCES)?.setOnPreferenceClickListener {
      BaseApplication.instance.openProjectRepo()
      true
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    requireActivity().onBackPressedDispatcher.addCallback(
      viewLifecycleOwner,
      object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          requireActivity().finish()
        }
      })
  }
}