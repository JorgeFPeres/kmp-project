package jorge.rickmortyapp.project.character.presentation

import androidx.lifecycle.ViewModel
import jorge.rickmortyapp.project.character.domain.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedCharacterViewModel: ViewModel() {
    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val selectedCharacter = _selectedCharacter.asStateFlow()

    fun onSelectCharacter(character: Character?) {
        _selectedCharacter.value = character
    }
}