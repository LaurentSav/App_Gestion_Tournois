function simpleSearch(){
    /* On récupère le tbody contenant les tournois */
    const tb = document.getElementById("displayedTournaments");

    /* La recherche effectuée */
    const keyword = document.getElementById("searchtext");

    /* Le nombre d'éléments affichés */
    const len = tb.querySelectorAll('tr').length
    for(var i = 0; i<len; i++) {
        if(tb.getElementsByTagName("tr")[i].getElementsByTagName("td")[0].innerHTML.includes(keyword.value) === true) {
            tb.getElementsByTagName("tr")[i].style.display='';
        } else{
            tb.getElementsByTagName("tr")[i].style.display='none';
        }
    }
}