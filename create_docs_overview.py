import os
from pathlib import Path
from collections import defaultdict
import argparse


def main(out_dir):
    out_dir_path = Path(out_dir)
    summary = [
        '\n',
        f'### Overview',
        '\n',
    ]

    summaries = defaultdict(list)
    paths = list(out_dir_path.iterdir())
    paths.sort()
    for path in paths:
        new_path = Path(str(path).replace('ai::lucidtech::las::sdk::', ''))
        link_name = new_path.stem
        readme_string = f'* [{link_name}]({str(new_path.name)})'
        if link_name.endswith('Client'):
            summary.extend([
                f'The [Client]({path.name}) class contains all the higher level methods',
                '\n',
            ])
        elif 'Options' in link_name:
            summaries['options'].append(readme_string)
        else:
            summaries['other'].append(readme_string)

        new_path.write_text(path.read_text())
        os.remove(str(path))

    summary.extend(
        [
            f'### Options',
            '\n',
        ] +
        summaries['options'] +
        [
            f'### Other',
            '\n',
        ] +
        summaries['other']
    )

    (out_dir_path / 'README.md').write_text('\n'.join(summary))


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('out_dir')
    args = parser.parse_args()

    main(**vars(args))
