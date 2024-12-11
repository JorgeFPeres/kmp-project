package jorge.rickmortyapp.project.character.data.mappers


import jorge.rickmortyapp.project.character.data.database.CharacterEntity
import jorge.rickmortyapp.project.character.data.dto.CharacterObj
import jorge.rickmortyapp.project.character.data.dto.SearchCharacterDto
import jorge.rickmortyapp.project.character.domain.Character

fun SearchCharacterDto.toCharacter(): Character {
    return Character(
        id = id.toString(),
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        image = image,
        episode = episode
    )
}

fun Character.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = id.toString(),
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        originName = origin.name,
        locationName = location.name,
        image = image,
        episode = episode
    )
}

fun CharacterEntity.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = CharacterObj(name = originName, url = ""),
        location = CharacterObj(name = locationName, url = ""),
        image = image,
        episode = episode
    )
}


