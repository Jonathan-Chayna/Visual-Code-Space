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

package com.teixeira.vcspace.preferences.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

/** @author Felipe Teixeira */
class GenericPreferencesFragment : PreferenceFragmentCompat() {

  companion object {
    const val KEY_RESOURCE_ID = "resource_id"

    @JvmStatic
    fun create(resourceId: Int): GenericPreferencesFragment {
      return GenericPreferencesFragment().also {
        it.arguments = Bundle().apply { putInt(KEY_RESOURCE_ID, resourceId) }
      }
    }
  }

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    val resourceId =
      arguments?.getInt(KEY_RESOURCE_ID)
        ?: throw IllegalStateException(
          "Preferences fragment cannot be launched without a resource ID"
        )
    setPreferencesFromResource(resourceId, rootKey)
  }
}